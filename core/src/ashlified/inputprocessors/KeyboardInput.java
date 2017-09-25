package ashlified.inputprocessors;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Interprets player's keyboard input. Changes the player's position, orientation and looking direction.
 */
public class KeyboardInput extends InputAdapter {

    private Entity controlledEntity;
    private PositionComponent posComp;
    private MovingDirectionComponent movDir;
    private LookingDirectionComponent lookDir;
    private TurnEndSignal endTurnSignal;

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
                Gdx.app.exit();
                break;
            case Input.Keys.COMMA:
                endTurnSignal.dispatch(null);
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
        if (adjacentSection.getOccupyingObjects().size() <= 0) {
            moveToSection(adjacentSection);
        }
    }

    private void moveToSection(DungeonSection section) {
        posComp.getOccupiedSection().getOccupyingObjects().remove(controlledEntity);
        posComp.setOccupiedSection(section);
        posComp.getPosition().set(section.getPosition().x, 6.5f, section.getPosition().z);
        section.addOccupyingObject(controlledEntity);
        endTurnSignal.dispatch(null);
    }
}


