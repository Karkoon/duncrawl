package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public class PositionSystem extends IteratingSystem {

  private final static float INTERPOLATION_ALPHA = 0.2f;
  private static Vector3 temp = new Vector3();
  private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

  PositionSystem() {
    super(Family.all(PositionComponent.class).exclude(PlayerComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    PositionComponent posComp = posMapper.get(entity);
    DungeonSection currentSection = posComp.getOccupiedSection();
    Vector3 sectionPosition = currentSection.getPosition();
    posComp.getPosition().interpolate(temp.set(sectionPosition.x, posComp.getPosition().y, sectionPosition.z), INTERPOLATION_ALPHA, Interpolation.linear);
  }
}
