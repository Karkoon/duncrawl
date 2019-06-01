package ashlified.gui;

import ashlified.entitycomponentsystem.components.InventoryComponent;
import ashlified.entitycomponentsystem.components.ModelInstanceComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;

public class CharacterWindow extends Window {

  private ComponentMapper<InventoryComponent> inventoryMapper = ComponentMapper.getFor(InventoryComponent.class);
  private ComponentMapper<ModelInstanceComponent> modelMapper = ComponentMapper.getFor(ModelInstanceComponent.class);

  private InventoryGrid inventoryGrid;

  CharacterWindow(Skin skin, Entity player) {
    super("Status Window", skin);
    super.setMovable(false);

    padLeft(150);
    padRight(150);
    InventoryComponent inventory = inventoryMapper.get(player);
    inventoryGrid = new InventoryGrid(inventory, skin);
    Image image = new Image(skin, "check-box");
    add(image).size(300, 300).padRight(30);
    add(inventoryGrid);
    row();
    Button closeButton = UserInterfaceStage.createFunctionalButton(skin, () -> setVisible(false));
    add(closeButton).size(100, 100);
    pack();
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible) inventoryGrid.update();
  }

  private static class InventorySlot extends Container<ModelInstanceImage> {

    private Drawable hover;

    InventorySlot(InventorySlotStyle style) {
      super();
      setTouchable(Touchable.enabled);
      size(100, 100);
      background(style.background);
      hover(style.hover, style.background);
    }

    public void hover(Drawable hover, Drawable background) {
      this.hover = hover;

      addListener(new ClickListener() {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
          super.enter(event, x, y, pointer, fromActor);
          background(hover);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
          super.exit(event, x, y, pointer, toActor);
          background(background);
        }
      });
    }

    private static class InventorySlotStyle {

      private Drawable background;
      private Drawable hover;

      public InventorySlotStyle() {
      }

      public InventorySlotStyle(Drawable background, Drawable hover) {
        this.background = background;
        this.hover = hover;
      }

      public InventorySlotStyle(InventorySlotStyle style) {
        this.background = style.background;
        this.hover = style.hover;
      }

      public Drawable getHover() {
        return hover;
      }

      public void setHover(Drawable hover) {
        this.hover = hover;
      }

      public Drawable getBackground() {
        return background;
      }

      public void setBackground(Drawable background) {
        this.background = background;
      }
    }

  }

  private class InventoryGrid extends Table {

    private InventoryComponent inventory;

    private ArrayList<InventorySlot> slots = new ArrayList<>();
    private ArrayList<Entity> itemsInInventory = new ArrayList<>();

    private DragAndDrop dragAndDrop = new DragAndDrop();

    InventoryGrid(InventoryComponent component, Skin skin) {
      super(skin);
      this.inventory = component;
      int columnCounter = 1;
      int numberOfColumns = 3;

      java.util.Stack<Entity> itemStack = new java.util.Stack<>();
      itemStack.addAll(component.getItems());

      dragAndDrop.setDragTime(0);

      for (int i = 0; i < component.getMaxItems(); i++) {
        InventorySlot slot = new InventorySlot(getSkin().get(InventorySlot.InventorySlotStyle.class));
        dragAndDrop.addTarget(new DragAndDrop.Target(slot) {
          @Override
          public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            boolean isFilled = ((Container) getActor()).hasChildren();
            return !isFilled;
          }

          @Override
          public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            Gdx.app.log("item", "drop");
            slot.removeActor(source.getActor());
            ((Container) getActor()).setActor(source.getActor());
          }
        });

        if (!itemStack.empty()) {
          Entity entity = itemStack.pop();
          ModelInstanceImage modelInstanceImage = new ModelInstanceImage(modelMapper.get(entity).getModelInstance());
          dragAndDrop.addSource(new DragAndDrop.Source(modelInstanceImage) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
              return new DragAndDrop.Payload();
            }
          });
          slot.setActor(modelInstanceImage);
        }
        slots.add(slot);

        add(slot).pad(10);
        if (columnCounter % numberOfColumns == 0) {
          row();
          columnCounter = 0;
        }
        columnCounter++;
      }
      pack();
    }


    public void update() {
      java.util.Stack<Entity> stack = new java.util.Stack<>();
      stack.addAll(inventory.getItems());
      while (!stack.empty()) {
        Entity entity = stack.pop();
        if (!itemsInInventory.contains(entity)) {
          itemsInInventory.add(entity);
          ModelInstanceImage modelInstanceImage = new ModelInstanceImage(modelMapper.get(entity).getModelInstance());

          dragAndDrop.addSource(new DragAndDrop.Source(modelInstanceImage) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
              return new DragAndDrop.Payload();
            }
          });

          for (InventorySlot slot : slots) {
            if (!(slot.hasChildren())) {
              slot.setActor(modelInstanceImage);
              break;
            }
          }
        }
      }
    }

  }
}


