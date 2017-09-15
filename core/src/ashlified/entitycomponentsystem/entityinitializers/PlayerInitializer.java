package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.*;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

/**
 * Creates and configures a Player. There can't be more than one players in the game.
 */
public class PlayerInitializer {

    private PooledEngine engine;

    public PlayerInitializer(PooledEngine engine) {
        this.engine = engine;
    }

    public Entity createPlayer(DungeonSection start) {
        StatsComponent stats = engine.createComponent(StatsComponent.class);
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.setPosition(new Vector3(start.getPosition().x, 6.5f, start.getPosition().z));
        position.setOccupiedSection(start);
        DirectionComponent direction = engine.createComponent(DirectionComponent.class);
        direction.setDirection(CardinalDirection.NORTH);
        InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
        ArmorComponent armor = engine.createComponent(ArmorComponent.class);
        AttackComponent attack = engine.createComponent(AttackComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);
        PointLightComponent pointLight = engine.createComponent(PointLightComponent.class);
        pointLight.setPointLight(new PointLight());
        pointLight.getPointLight().set(255, 255, 255, position.getPosition(), 100f);

        Entity entity = engine.createEntity();
        entity.add(stats);
        entity.add(position);
        entity.add(direction);
        entity.add(inventory);
        entity.add(armor);
        entity.add(attack);
        entity.add(health);
        entity.add(pointLight);

        start.addOccupyingObject(entity);

        engine.addEntity(entity);

        return entity;
    }

}
