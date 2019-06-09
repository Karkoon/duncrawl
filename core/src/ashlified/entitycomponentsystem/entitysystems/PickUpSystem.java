package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.DroppedItemComponent;
import ashlified.entitycomponentsystem.components.InventoryComponent;
import ashlified.entitycomponentsystem.components.PickUpIntentComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

public class PickUpSystem extends IteratingSystem {

  private ComponentMapper<PickUpIntentComponent> pickUpIntentMapper = ComponentMapper.getFor(PickUpIntentComponent.class);
  private ComponentMapper<InventoryComponent> inventoryMapper = ComponentMapper.getFor(InventoryComponent.class);
  private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

  PickUpSystem() {
    super(Family.all(PickUpIntentComponent.class, InventoryComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    if (isEntityAbleToPickUpItem(entity)) {
      Entity item = getTargetItemFrom(entity);
      removeItemFromDungeon(item);
      addItemToEntityInventory(entity, item);
      Gdx.app.log("Item picked up", item.toString());
    }
  }

  private Entity getTargetItemFrom(Entity entity) {
    PickUpIntentComponent pickUpIntentComponent = pickUpIntentMapper.get(entity);
    Entity item = pickUpIntentComponent.getItem();
    pickUpIntentComponent.reset();
    entity.remove(PickUpIntentComponent.class);
    return item;
  }

  private void addItemToEntityInventory(Entity entity, Entity item) {
    InventoryComponent inventoryComponent = inventoryMapper.get(entity);
    inventoryComponent.addItem(item);
  }

  private void removeItemFromDungeon(Entity item) {
    item.remove(DroppedItemComponent.class);
    posMapper.get(item).getOccupiedSection().getOccupyingEntities().remove(item);
    item.remove(PositionComponent.class);
  }

  private boolean isEntityAbleToPickUpItem(Entity entity) {
    InventoryComponent inventoryComponent = inventoryMapper.get(entity);
    return inventoryComponent.getItems().size() != inventoryComponent.getMaxItems();
  }
}
