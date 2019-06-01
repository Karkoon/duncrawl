package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.DroppedItemComponent;
import ashlified.entitycomponentsystem.components.FloatingSpeedComponent;
import ashlified.entitycomponentsystem.components.ModelInstanceComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.graphics.ModelInstanceRenderer;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.atan2;

public class ItemRendererSystem extends IteratingSystem {

  private ModelInstanceRenderer renderer;

  private ComponentMapper<ModelInstanceComponent> modelInstanceMapper = ComponentMapper.getFor(ModelInstanceComponent.class);
  private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
  private ComponentMapper<FloatingSpeedComponent> floatingSpeedMapper = ComponentMapper.getFor(FloatingSpeedComponent.class);

  ItemRendererSystem(ModelInstanceRenderer renderer) {
    super(Family.all(DroppedItemComponent.class, ModelInstanceComponent.class, PositionComponent.class, FloatingSpeedComponent.class).get());
    this.renderer = renderer;
  }


  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    ModelInstance instance = modelInstanceMapper.get(entity).getModelInstance();
    Vector3 position = posMapper.get(entity).getPosition();
    float posX = position.x;
    float posZ = position.z;
    float posY = position.y;
    float floatingSpeed = floatingSpeedMapper.get(entity).getSpeed();
    position.y = posY + floatingSpeed * deltaTime;
    if (position.y > 5 || position.y < 3) floatingSpeedMapper.get(entity).setSpeed(-floatingSpeed);
    Vector3 cam = renderer.getCamera().position;
    float angle = (float) Math.toDegrees(atan2(cam.x - posX, cam.z - posZ));
    instance.transform.setToRotation(0, 1f, 0, angle);
    instance.transform.setTranslation(posX, posY, posZ);
    instance.transform.scale(0.25f, 0.25f, 0.25f);
    renderer.addToCache(instance);
  }
}
