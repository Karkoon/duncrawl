package ashlified.gui;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.AttackComponent;
import ashlified.entitycomponentsystem.components.DroppedItemComponent;
import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import ashlified.entitycomponentsystem.components.PickUpIntentComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.entitycomponentsystem.signals.PlayerTurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static ashlified.gui.UserInterface.createFunctionalButton;

class ControlButtonsTable extends Table {

  private static final int BUTTON_SIZE = 50;

  private PlayerTurnEndSignal playerTurnEndSignal;
  private PositionComponent currentPosition;
  private Entity controlledEntity;
  private PooledEngine engine;
  private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

  ControlButtonsTable(final Skin skin, final PooledEngine engine) {
    this.controlledEntity = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
    this.engine = engine;
    currentPosition = controlledEntity.getComponent(PositionComponent.class);
    this.playerTurnEndSignal = new PlayerTurnEndSignal(engine);

    final MovingDirectionComponent directionComponent = controlledEntity.getComponent(MovingDirectionComponent.class);
    final LookingDirectionComponent lookingDirectionComponent = controlledEntity.getComponent(LookingDirectionComponent.class);

    Button forwardButton = createFunctionalButton(skin, () -> moveIn(directionComponent.getDirection()));
    Button backButton = createFunctionalButton(skin, () -> moveIn(CardinalDirection.oppositeOf(directionComponent.getDirection())));
    Button rightButton = createFunctionalButton(skin, () -> {
      directionComponent.setDirection(CardinalDirection.rightOf(directionComponent.getDirection()));
      lookingDirectionComponent.setLookingDirection(directionComponent.getDirection().value.cpy());
    });
    Button leftButton = createFunctionalButton(skin, () -> {
      directionComponent.setDirection(CardinalDirection.leftOf(directionComponent.getDirection()));
      lookingDirectionComponent.setLookingDirection(directionComponent.getDirection().value.cpy());
    });

    Button actionButton = createFunctionalButton(skin, () -> processActionButton(directionComponent.getDirection()));

    add();
    add(forwardButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
    row();
    add(leftButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
    add(backButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
    add(rightButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
    add(actionButton).width(BUTTON_SIZE * 2).height(BUTTON_SIZE * 0.75f).padLeft(50);
    add().expandX().fillX();

  }

  private void processActionButton(CardinalDirection direction) {
    if (!attack(direction)) {
      pickUp(direction);
    }
  }

  private boolean attack(CardinalDirection direction) {
    Family enemyFamily = Family.all(SpriterModelComponent.class).get();
    Connection<DungeonSection> connection = currentPosition.getOccupiedSection().getConnection(direction);
    if (connection != null && connection.getToNode().getOccupyingEntities().size() > 0 && enemyFamily.matches(connection.getToNode().getOccupyingEntities().get(0))) {
      Entity enemy = connection.getToNode().getOccupyingEntities().get(0);
      if (healthMapper.get(enemy).getHealth() > 0) {
        AttackComponent attack = engine.createComponent(AttackComponent.class);
        attack.setEnemy(enemy);
        controlledEntity.add(attack);
        playerTurnEndSignal.dispatch(null);
      }
      return true;
    }
    return false;
  }

  private boolean pickUp(CardinalDirection direction) {
    Family itemFamily = Family.all(DroppedItemComponent.class).get();
    Connection<DungeonSection> connection = currentPosition.getOccupiedSection().getConnection(direction);
    if (connection != null && connection.getToNode().getOccupyingEntities().size() > 0 && itemFamily.matches(connection.getToNode().getOccupyingEntities().get(0))) {
      Entity item = connection.getToNode().getOccupyingEntities().get(0);
      PickUpIntentComponent pickUpIntent = engine.createComponent(PickUpIntentComponent.class);
      pickUpIntent.setItem(item);
      controlledEntity.add(pickUpIntent);
      playerTurnEndSignal.dispatch(null);
      return true;
    }
    return false;
  }

  private void moveIn(CardinalDirection direction) {
    DungeonSection currentSection = currentPosition.getOccupiedSection();
    DungeonSection adjacentSection = currentSection;
    if (currentSection.getConnection(direction) != null) {
      adjacentSection = currentSection.getConnection(direction).getToNode();
    }
    if (adjacentSection.getOccupyingEntities().size() <= 0) {
      moveToSection(adjacentSection, currentPosition);
    }
  }

  private void moveToSection(DungeonSection section, PositionComponent currentPosition) {
    currentPosition.getOccupiedSection().getOccupyingEntities().remove(controlledEntity);
    currentPosition.setOccupiedSection(section);
    currentPosition.getPosition().set(section.getPosition().x, 6.5f, section.getPosition().z);
    section.addOccupyingObject(controlledEntity);
    playerTurnEndSignal.dispatch(null);
  }
}