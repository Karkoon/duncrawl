package ashlified.inputprocessors;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.*;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Interprets player's keyboard input. Changes the player's position, orientation and looking direction.
 */
public class KeyboardInput extends InputAdapter {

    private Entity controlledEntity;
    private PositionComponent posComp;
    private MovingDirectionComponent movDir;
    private LookingDirectionComponent lookDir;
    private TurnEndSignal endTurnSignal;
    private boolean inputLocked = false;

    public KeyboardInput(TurnEndSignal endTurnSignal, Entity controlledEntity) {
        this.endTurnSignal = endTurnSignal;
        this.controlledEntity = controlledEntity;
        ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<MovingDirectionComponent> movingDirectionMapper = ComponentMapper.getFor(MovingDirectionComponent.class);
        ComponentMapper<LookingDirectionComponent> lookingDirectionMapper = ComponentMapper.getFor(LookingDirectionComponent.class);
        posComp = posMapper.get(this.controlledEntity);
        movDir = movingDirectionMapper.get(this.controlledEntity);
        lookDir = lookingDirectionMapper.get(this.controlledEntity);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!inputLocked) {
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
                case Input.Keys.COMMA:
                    endTurnSignal.dispatch(null);
            }
        }
        return true;
    }

    private void attack(CardinalDirection direction) {
        Family enemyFamily = Family.all(SpriterModelComponent.class).get();
        Connection<DungeonSection> connection = posComp.getOccupiedSection().getConnection(direction);
        if (connection != null && connection.getToNode().getOccupyingEntities().size() > 0 && enemyFamily.matches(connection.getToNode().getOccupyingEntities().get(0))) {
            Entity enemy = connection.getToNode().getOccupyingEntities().get(0);

            HealthComponent enemyHealth = enemy.getComponent(HealthComponent.class);
            StatsComponent npcStats = controlledEntity.getComponent(StatsComponent.class);
            enemyHealth.setHealth(enemyHealth.getHealth() - npcStats.getStrength());
            SpriterModelComponent spriterModelComponent = enemy.getComponent(SpriterModelComponent.class);
            spriterModelComponent.getSpriterAnimationController().damagedEvent();
            endTurnSignal.dispatch(null);
        }
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
        if (adjacentSection.getOccupyingEntities().size() <= 0) {
            moveToSection(adjacentSection);
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


