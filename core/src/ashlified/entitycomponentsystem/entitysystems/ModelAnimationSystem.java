package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.AnimationControllerComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Updates the animation state of the current animation timeline of a model instance.
 */
public class ModelAnimationSystem extends IteratingSystem {

  private ComponentMapper<AnimationControllerComponent> animationMapper = ComponentMapper.getFor(AnimationControllerComponent.class);

  public ModelAnimationSystem() {
    super(Family.all(AnimationControllerComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    AnimationControllerComponent animComp = animationMapper.get(entity);
    animComp.getController().update(deltaTime);
  }
}
