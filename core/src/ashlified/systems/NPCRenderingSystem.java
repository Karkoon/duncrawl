package ashlified.systems;

import ashlified.components.AnimationsComponent;
import ashlified.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCRenderingSystem extends IteratingSystem {

    private ComponentMapper<AnimationsComponent> animationsMapper = ComponentMapper.getFor(AnimationsComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    private DecalBatch batch;

    public NPCRenderingSystem(Family family, Camera camera) {
        super(family);
        batch = new DecalBatch(new CameraGroupStrategy(camera));
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationsComponent animComp = animationsMapper.get(entity);
        updateAnimationTime(animComp, deltaTime);
        Decal decal = getCurrentKeyFrame(animComp);
        Vector3 position = positionMapper.get(entity).getPosition();
        updateAnimationPosition(decal, position);
        batch.add(decal);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.flush();
    }

    private void updateAnimationPosition(Decal decal, Vector3 position) {
        decal.setPosition(position);
    }

    private void updateAnimationTime(AnimationsComponent animation, float deltaTime) {
        float time = animation.getTime() + deltaTime;
        animation.setTime(time);
    }

    private Decal getCurrentKeyFrame(AnimationsComponent animationsComponent) {
        Animation<Decal> animation = animationsComponent.getAnimationsMap().get(animationsComponent.getCurrentState());
        Decal currentFrame = animation.getKeyFrame(animationsComponent.getTime());
        return currentFrame;
    }
}
