package ashlified.systems;

import ashlified.components.AnimationsComponent;
import ashlified.components.PositionComponent;
import ashlified.spriterutils.DecalDrawer;
import com.badlogic.ashley.core.ComponentMapper;
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

    private final Camera camera;
    private ComponentMapper<AnimationsComponent> animationsMapper = ComponentMapper.getFor(AnimationsComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private DecalDrawer drawer;
    private DecalBatch batch;

    public NPCRenderingSystem(Family family, DecalBatch batch, Camera camera) {
        super(family);
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationsComponent animComp = animationsMapper.get(entity);
        PositionComponent posComp = positionMapper.get(entity);
        updateAnimationTime(animComp, deltaTime);
        updateAnimationPosition(animComp, posComp.getPosition());
        drawer = new DecalDrawer(animComp.getLoader(), batch, camera);
        drawer.draw(animComp.getPlayer());
    }

    private void updateAnimationPosition(AnimationsComponent animComp, Vector3 position) {
        animComp.getPlayer().setPosition(position.x, position.y);
    }

    private void updateAnimationTime(AnimationsComponent animation, float deltaTime) {
        animation.getPlayer().update();
    }
}
