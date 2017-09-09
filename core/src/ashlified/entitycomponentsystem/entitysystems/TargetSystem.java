package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.TargetComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Updates enemy's target position to the new player position.
 */
public class TargetSystem extends IteratingSystem {

    private PositionComponent targetPosition;
    private ComponentMapper<TargetComponent> targetMapper = ComponentMapper.getFor(TargetComponent.class);

    public TargetSystem(PositionComponent targetPosition) {
        super(Family.all(TargetComponent.class).get());
        this.targetPosition = targetPosition;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        targetMapper.get(entity).setTarget(targetPosition.getOccupiedSection());
    }
}
