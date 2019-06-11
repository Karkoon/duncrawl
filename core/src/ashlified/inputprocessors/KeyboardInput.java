package ashlified.inputprocessors;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.AttackComponent;
import ashlified.entitycomponentsystem.components.DroppedItemComponent;
import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import ashlified.entitycomponentsystem.components.PickUpIntentComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.entitycomponentsystem.signals.PlayerTurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Interprets player's keyboard input. Changes the player's position, orientation and looking direction.
 */
public class KeyboardInput extends InputAdapter {

  private Entity controlledEntity;
  private PooledEngine engine;
  private PositionComponent posComp;
  private MovingDirectionComponent movDir;
  private LookingDirectionComponent lookDir;
  private PlayerTurnEndSignal endTurnSignal;

  private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

  public KeyboardInput(Entity controlledEntity, PooledEngine engine) {
    this.endTurnSignal = new PlayerTurnEndSignal(engine);
    this.controlledEntity = controlledEntity;
    this.engine = engine;
    ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<MovingDirectionComponent> movingDirectionMapper = ComponentMapper.getFor(MovingDirectionComponent.class);
    ComponentMapper<LookingDirectionComponent> lookingDirectionMapper = ComponentMapper.getFor(LookingDirectionComponent.class);
    posComp = posMapper.get(this.controlledEntity);
    movDir = movingDirectionMapper.get(this.controlledEntity);
    lookDir = lookingDirectionMapper.get(this.controlledEntity);
  }

  @Override
  public boolean keyDown(int keycode) {
    CardinalDirection direction = movDir.getDirection();
    switch (keycode) {
      case Input.Keys.W:
        moveIn(direction);
        break;
      case Input.Keys.S:
        moveIn(CardinalDirection.oppositeOf(direction));
        break;
      case Input.Keys.A:
        rotateTo(CardinalDirection.leftOf(direction));
        break;
      case Input.Keys.D:
        rotateTo(CardinalDirection.rightOf(direction));
        break;
      case Input.Keys.SPACE:
        attack(direction);
        break;
      case Input.Keys.E:
        pickUp(direction);
        break;
      case Input.Keys.COMMA:
        endTurnSignal.dispatch(null);
        break;
      default:
        break;
    }
    return true;
  }

  private void rotateTo(CardinalDirection direction) {
    movDir.setDirection(direction);
    lookDir.setLookingDirection(movDir.getDirection().value.cpy());
  }

  private void moveIn(CardinalDirection direction) {
    DungeonSection currentSection = posComp.getOccupiedSection();
    DungeonSection adjacentSection = currentSection;
    if (currentSection.getConnection(direction) != null) {
      adjacentSection = currentSection.getConnection(direction).getToNode();
    }
    if (adjacentSection.getOccupyingEntities().isEmpty()) {
      moveToSection(adjacentSection);
    }
  }

  private void attack(CardinalDirection direction) {
    Family enemyFamily = Family.all(SpriterModelComponent.class).get();
    Connection<DungeonSection> connection = posComp.getOccupiedSection().getConnection(direction);
    if (connection != null && connection.getToNode().getOccupyingEntities().size() > 0 && enemyFamily.matches(connection.getToNode().getOccupyingEntities().get(0))) {
      Entity enemy = connection.getToNode().getOccupyingEntities().get(0);
      if (healthMapper.get(enemy).getHealth() > 0) {
        AttackComponent attack = engine.createComponent(AttackComponent.class);
        attack.setEnemy(enemy);
        controlledEntity.add(attack);
        endTurnSignal.dispatch(null);
      }
    }
  }

  private void pickUp(CardinalDirection direction) {
    Family itemFamily = Family.all(DroppedItemComponent.class).get();
    Connection<DungeonSection> connection = posComp.getOccupiedSection().getConnection(direction);
    if (connection != null && connection.getToNode().getOccupyingEntities().size() > 0 && itemFamily.matches(connection.getToNode().getOccupyingEntities().get(0))) {
      Entity item = connection.getToNode().getOccupyingEntities().get(0);
      PickUpIntentComponent pickUpIntent = engine.createComponent(PickUpIntentComponent.class);
      pickUpIntent.setItem(item);
      controlledEntity.add(pickUpIntent);
      endTurnSignal.dispatch(null);
    }
  }

  private void moveToSection(DungeonSection section) {
    posComp.getOccupiedSection().getOccupyingEntities().remove(controlledEntity);
    posComp.setOccupiedSection(section);
    posComp.getPosition().set(section.getPosition().x, 6.5f, section.getPosition().z);
    section.addOccupyingObject(controlledEntity);
    endTurnSignal.dispatch(null);
  }
}


