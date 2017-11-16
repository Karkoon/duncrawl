package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;

public class PositionSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private final static float INTERPOLATION_ALPHA = 0.2f;

    PositionSystem() {
        super(Family.all(PositionComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent posComp = posMapper.get(entity);
        DungeonSection currentSection = posComp.getOccupiedSection();
        posComp.getPosition().interpolate(currentSection.getPosition(), INTERPOLATION_ALPHA, Interpolation.linear);
    }
}
