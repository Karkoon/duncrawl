package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.DirectionComponent;
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
        ashlified.entitycomponentsystem.components.StatsComponent stats = engine.createComponent(ashlified.entitycomponentsystem.components.StatsComponent.class);
        ashlified.entitycomponentsystem.components.PositionComponent position = engine.createComponent(ashlified.entitycomponentsystem.components.PositionComponent.class);
        position.setPosition(new Vector3(start.getPosition().x, 6.5f, start.getPosition().z));
        position.setOccupiedSection(start);
        ashlified.entitycomponentsystem.components.DirectionComponent direction = engine.createComponent(DirectionComponent.class);
        direction.setDirection(CardinalDirection.NORTH);
        ashlified.entitycomponentsystem.components.InventoryComponent inventory = engine.createComponent(ashlified.entitycomponentsystem.components.InventoryComponent.class);
        ashlified.entitycomponentsystem.components.ArmorComponent armor = engine.createComponent(ashlified.entitycomponentsystem.components.ArmorComponent.class);
        ashlified.entitycomponentsystem.components.AttackComponent attack = engine.createComponent(ashlified.entitycomponentsystem.components.AttackComponent.class);
        ashlified.entitycomponentsystem.components.HealthComponent health = engine.createComponent(ashlified.entitycomponentsystem.components.HealthComponent.class);
        ashlified.entitycomponentsystem.components.PointLightComponent pointLight = engine.createComponent(ashlified.entitycomponentsystem.components.PointLightComponent.class);
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
