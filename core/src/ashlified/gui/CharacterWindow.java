package ashlified.gui;

import ashlified.entitycomponentsystem.components.InventoryComponent;
import ashlified.entitycomponentsystem.components.ItemIconComponent;
import ashlified.entitycomponentsystem.components.ItemTypeComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterWindow extends Window {

  private ComponentMapper<InventoryComponent> inventoryMapper = ComponentMapper.getFor(InventoryComponent.class);
  private ComponentMapper<ItemTypeComponent> itemTypeMapper = ComponentMapper.getFor(ItemTypeComponent.class);
  private ComponentMapper<ItemIconComponent> itemIconMapper = ComponentMapper.getFor(ItemIconComponent.class);

  private InventoryGrid inventoryGrid;

  CharacterWindow(Skin skin, Entity player) {
    super("Status Window", skin);
    super.setMovable(false);

    padLeft(30);
    padRight(30);

    DragAndDrop dragAndDrop = new DragAndDrop();
    dragAndDrop.setDragActorPosition(25, -25);

    InventoryComponent inventory = inventoryMapper.get(player);
    inventoryGrid = new InventoryGrid(inventory, skin, dragAndDrop);
    Image image = new Image(skin.getDrawable("player"));
    Stack equippedInventory = new Stack();
    equippedInventory.add(image);
    equippedInventory.add(new UsedEquipmentSlots(skin, dragAndDrop));
    add(equippedInventory).width(200).fillY().pad(5).padLeft(20);
    add(inventoryGrid);
    row();
    Button closeButton = UserInterface.createFunctionalButton(skin, () -> setVisible(false));
    add(closeButton).size(150, 50).colspan(2);
    pack();
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible) inventoryGrid.update();
  }

  private static class UsedEquipmentSlots extends Table {

    UsedEquipmentSlots(Skin skin, DragAndDrop dragAndDrop) {
      InventorySlot.InventorySlotStyle style = skin.get(InventorySlot.InventorySlotStyle.class);
      InventorySlot head = new InventorySlot(style, ItemTypeComponent.Type.HELMET);
      InventorySlot leftArm = new InventorySlot(style, ItemTypeComponent.Type.LEFT_WEAPON);
      InventorySlot chest = new InventorySlot(style, ItemTypeComponent.Type.ARMOR);
      InventorySlot rightArm = new InventorySlot(style, ItemTypeComponent.Type.RIGHT_WEAPON);
      InventorySlot legs = new InventorySlot(style, ItemTypeComponent.Type.TROUSERS);
      InventorySlot feet = new InventorySlot(style, ItemTypeComponent.Type.BOOTS);

      List<InventorySlot> inventorySlots = Arrays.asList(head, leftArm, chest, rightArm, legs, feet);

      for (InventorySlot slot : inventorySlots) {
        dragAndDrop.addTarget(new InventorySlot.InventorySlotTarget(slot));
      }

      add(head).colspan(3).padBottom(10);
      row().padBottom(-30);
      add(chest).size(70).colspan(3);
      row().padBottom(-10);
      add(leftArm);
      add(rightArm).padLeft(110);
      row().padBottom(30);
      add(legs).colspan(3);
      row();
      add(feet).colspan(3);
    }
  }


  private class InventoryGrid extends Table {

    private InventoryComponent inventory;
    private ArrayList<InventorySlot> slots = new ArrayList<>();
    private ArrayList<Entity> itemsInInventory = new ArrayList<>();
    private static final int NUMBER_OF_COLUMNS = 3;
    private DragAndDrop dragAndDrop;

    InventoryGrid(InventoryComponent component, Skin skin, DragAndDrop dragAndDrop) {
      super(skin);
      this.inventory = component;
      this.dragAndDrop = dragAndDrop;
      this.dragAndDrop.setDragTime(0);

      for (int i = 0; i < component.getMaxItems(); i++) {
        InventorySlot slot = new InventorySlot(getSkin().get(InventorySlot.InventorySlotStyle.class), ItemTypeComponent.Type.ANY);
        this.dragAndDrop.addTarget(new InventorySlot.InventorySlotTarget(slot));
        this.slots.add(slot);
        add(slot).pad(10);

        boolean limitOfColumnsReached = i % NUMBER_OF_COLUMNS == NUMBER_OF_COLUMNS - 1;
        if (limitOfColumnsReached) {
          row();
        }
      }
    }

    void update() {
      ArrayList<Entity> items = new ArrayList<>(inventory.getItems());
      for (Entity item : items) {
        if (!itemsInInventory.contains(item)) {
          itemsInInventory.add(item);
          Image itemImage = new Image(itemIconMapper.get(item).getIcon());
          dragAndDrop.addSource(createDragAndDropSource(itemImage, item));
          getEmptySlot().setActor(itemImage);
        }
      }
    }

    private InventorySlot getEmptySlot() throws IllegalStateException {
      for (InventorySlot slot : slots) {
        if (slot.isEmpty()) {
          return slot;
        }
      }
      return null;
    }

    DragAndDrop.Source createDragAndDropSource(Image itemImage, Entity item) {
      return new DragAndDrop.Source(itemImage) {
        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
          DragAndDrop.Payload payload = new DragAndDrop.Payload();
          payload.setObject(itemTypeMapper.get(item).getType());
          itemImage.setVisible(false);
          Image dragImage = new Image(itemImage.getDrawable());
          dragImage.setSize(50, 50);
          payload.setDragActor(dragImage);
          return payload;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
          super.dragStop(event, x, y, pointer, payload, target);
          itemImage.setVisible(true);
        }
      };
    }
  }

}


