package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.DirectionComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.entitycomponentsystem.components.TargetComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Updates enemy's target position to the new player position.
 */
public class TargetSystem extends IteratingSystem {

    private PositionComponent targetPosition;
    private ComponentMapper<TargetComponent> targetMapper = ComponentMapper.getFor(TargetComponent.class);

    public TargetSystem() {
        super(Family.all(TargetComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        obtainPlayerPosition(engine);
        super.addedToEngine(engine);
    }

    private void obtainPlayerPosition(Engine engine) {
        targetPosition = engine.getEntitiesFor(Family.all(PositionComponent.class, DirectionComponent.class)
                .exclude(SpriterModelComponent.class)
                .get()).first().getComponent(PositionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        targetMapper.get(entity).setTarget(targetPosition.getOccupiedSection());
    }
}
