package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class DeadEntitiesRemoverSystem extends IteratingSystem {

    private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    DeadEntitiesRemoverSystem() {
        super(Family.all(HealthComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (healthMapper.get(entity).getHealth() <= 0) {
            positionMapper.get(entity).getOccupiedSection().getOccupyingEntities().remove(entity);
            getEngine().removeEntity(entity);
        }
    }
}
