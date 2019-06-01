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

  public PickUpSystem() {
    super(Family.all(PickUpIntentComponent.class, InventoryComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    PickUpIntentComponent pickUpIntentComponent = pickUpIntentMapper.get(entity);
    Entity item = pickUpIntentComponent.getItem();
    item.remove(DroppedItemComponent.class); // pick up implies an item?
    posMapper.get(item).getOccupiedSection().getOccupyingEntities().remove(item);
    item.remove(PositionComponent.class);
    inventoryMapper.get(entity).addItem(item);
    entity.remove(PickUpIntentComponent.class);
    Gdx.app.log("Item picked up", item.toString());

  }
}
