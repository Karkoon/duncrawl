package ashlified.systems;

import ashlified.components.*;
import ashlified.dungeon.DungeonSection;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 25.03.17.
 */
public class PlayerSystem extends EntitySystem {


    private DungeonSection start;

    public PlayerSystem(DungeonSection spawnPoint) {
        super();
        this.start = spawnPoint;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createPlayer();
    }

    private void createPlayer() {
        PooledEngine engine = (PooledEngine) getEngine();

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
        pointLight.getPointLight().set(255, 255, 255, position.getPosition(), 100);

        Entity entity = ((PooledEngine) getEngine()).createEntity();
        entity.add(stats);
        entity.add(position);
        entity.add(direction);
        entity.add(inventory);
        entity.add(armor);
        entity.add(attack);
        entity.add(health);
        entity.add(pointLight);

        getEngine().addEntity(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

}
