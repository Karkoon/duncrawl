package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.graphics.spriterutils.SpriterAnimationController;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class DeadEntitiesRemoverSystem extends IteratingSystem {

    private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SpriterModelComponent> spriterMapper = ComponentMapper.getFor(SpriterModelComponent.class);

    DeadEntitiesRemoverSystem() {
        super(Family.all(HealthComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        int entityHealth = healthMapper.get(entity).getHealth();
        SpriterAnimationController animationController = spriterMapper.get(entity).getSpriterAnimationController();
        if (entityHealth <= 0 && !animationController.isLocked()) {
            positionMapper.get(entity).getOccupiedSection().getOccupyingEntities().remove(entity);
            getEngine().removeEntity(entity);
        }
    }
}
