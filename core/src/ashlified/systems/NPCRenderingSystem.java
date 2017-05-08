package ashlified.systems;

import ashlified.Graphics;
import ashlified.components.AnimationsComponent;
import ashlified.components.PositionComponent;
import ashlified.spriterutils.DecalDrawer;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCRenderingSystem extends IteratingSystem {

    private final Graphics graphics;
    private ComponentMapper<AnimationsComponent> animationsMapper = ComponentMapper.getFor(AnimationsComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    public NPCRenderingSystem(Family family, Graphics graphics) {
        super(family);
        this.graphics = graphics;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        for (Entity entity : engine.getEntitiesFor(getFamily())) {
            AnimationsComponent animComp = animationsMapper.get(entity);
            ((DecalDrawer) animComp.getDrawer()).setGraphics(graphics);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationsComponent animComp = animationsMapper.get(entity);
        PositionComponent posComp = positionMapper.get(entity);
        updateAnimationTime(animComp, deltaTime);
        updateAnimationPosition(animComp, posComp.getPosition());
        animComp.getDrawer().draw(animComp.getPlayer());
    }

    private void updateAnimationPosition(AnimationsComponent animComp, Vector3 position) {
        animComp.getPlayer().setPosition(position.x, position.z);
    }

    private void updateAnimationTime(AnimationsComponent animation, float deltaTime) {
        animation.getPlayer().update();
    }
}
