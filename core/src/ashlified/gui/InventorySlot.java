package ashlified.gui;

import ashlified.entitycomponentsystem.components.ItemTypeComponent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

class InventorySlot extends Container<Image> {

  private final static int INVENTORY_SLOT_WIDTH = 50;
  private final static int INVENTORY_SLOT_HEIGHT = 50;

  private ItemTypeComponent.Type type;

  InventorySlot(InventorySlotStyle style, ItemTypeComponent.Type type) {
    super();
    this.type = type;
    setTouchable(Touchable.enabled);
    size(INVENTORY_SLOT_WIDTH, INVENTORY_SLOT_HEIGHT);
    applyStyle(style);
  }

  private void applyStyle(InventorySlotStyle style) {
    background(style.background);
    onHover(style.hover, style.background);
  }

  boolean isEmpty() {
    return !hasChildren();
  }

  public ItemTypeComponent.Type getType() {
    return type;
  }

  private void onHover(Drawable hover, Drawable background) {
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

  /**
   * Used with DragAndDrop.
   */
  static class InventorySlotTarget extends DragAndDrop.Target {

    private InventorySlot slot;

    InventorySlotTarget(InventorySlot slot) {
      super(slot);
      this.slot = slot;
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
      boolean hasCompatibleType = slot.type == ItemTypeComponent.Type.ANY || slot.type == payload.getObject();
      return slot.isEmpty() && hasCompatibleType;
    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
      slot.removeActor(source.getActor());
      slot.setActor((Image) source.getActor());
    }
  }

  /**
   * Used for deserialization by Scene2D.
   * Contains relevant CSS-like properties.
   */
  static class InventorySlotStyle {

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
