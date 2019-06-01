package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public class CameraUpdateSystem extends EntitySystem {

  private Camera camera;
  private Entity observedEntity;
  private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
  private ComponentMapper<LookingDirectionComponent> lookingDirectionMapper = ComponentMapper.getFor(LookingDirectionComponent.class);

  CameraUpdateSystem(Camera camera) {
    this.camera = camera;
  }

  @Override
  public void addedToEngine(Engine engine) {
    super.addedToEngine(engine);
    Family family = Family
      .all(PositionComponent.class, PlayerComponent.class, LookingDirectionComponent.class)
      .get();
    ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
    if (entities.size() != 1) {
      Gdx.app.error("InputSystem", "No controllable entity or more than one entity");
      Gdx.app.exit();
    }
    observedEntity = entities.first();
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    Vector3 position = posMapper.get(observedEntity).getPosition();
    Vector3 lookingDirection = lookingDirectionMapper.get(observedEntity).getLookingDirection();
    camera.position.interpolate(position, 0.2f, Interpolation.linear);
    camera.direction.lerp(lookingDirection, 0.125f);
    camera.update();
  }
}
