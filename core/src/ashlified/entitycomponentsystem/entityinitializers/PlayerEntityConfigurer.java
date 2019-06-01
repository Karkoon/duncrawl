package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.ArmorComponent;
import ashlified.entitycomponentsystem.components.AttackActionComponent;
import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.InventoryComponent;
import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PointLightComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.StatsComponent;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

/**
 * Obtains an Entity and Components and configures them into a Player.
 * There can't be more than one player in the game.
 */
class PlayerEntityConfigurer {

  private PooledEngine engine;

  PlayerEntityConfigurer(PooledEngine engine) {
    this.engine = engine;
  }

  void configurePlayer(DungeonSection start) {
    StatsComponent stats = engine.createComponent(StatsComponent.class);
    stats.setStrength(5);
    stats.setDexterity(10);
    stats.setWisdom(10);
    PositionComponent position = engine.createComponent(PositionComponent.class);
    position.setPosition(new Vector3(start.getPosition().x, 6.5f, start.getPosition().z));
    position.setOccupiedSection(start);
    MovingDirectionComponent direction = engine.createComponent(MovingDirectionComponent.class);
    direction.setDirection(CardinalDirection.NORTH);
    InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
    inventory.setMaxItems(12);
    ArmorComponent armor = engine.createComponent(ArmorComponent.class);
    HealthComponent health = engine.createComponent(HealthComponent.class);
    health.setMaxHealth(20);
    health.setHealth(20);
    PlayerComponent player = engine.createComponent(PlayerComponent.class);
    PointLightComponent pointLight = engine.createComponent(PointLightComponent.class);
    pointLight.setPointLight(new PointLight());
    pointLight.getPointLight().set(255, 255, 255, position.getPosition(), 150f);
    LookingDirectionComponent lookingDirection = engine.createComponent(LookingDirectionComponent.class);
    lookingDirection.setLookingDirection(direction.getDirection().value.cpy());
    AttackActionComponent attackActionComponent = engine.createComponent(AttackActionComponent.class);
    attackActionComponent.setAction(() -> {});
    attackActionComponent.setReaction(() -> {});

    Entity entity = engine.createEntity();
    entity.add(stats)
      .add(position)
      .add(direction)
      .add(inventory)
      .add(armor)
      .add(health)
      .add(pointLight)
      .add(player)
      .add(attackActionComponent)
      .add(lookingDirection);

    start.addOccupyingObject(entity);

    engine.addEntity(entity);
  }

}
