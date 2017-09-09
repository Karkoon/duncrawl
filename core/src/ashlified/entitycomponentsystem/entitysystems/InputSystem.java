package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.DirectionComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Contains data necessary for processing input. Moves the camera to the player's position.
 */
public class InputSystem extends EntitySystem {

    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DirectionComponent> directionMapper = ComponentMapper.getFor(DirectionComponent.class);
    private Entity controlledEntity;
    private Camera camera;
    private TurnEndSignal endTurnSignal;
    private boolean cameraLockOverride = false;

    public InputSystem(Camera camera, TurnEndSignal endTurnSignal) {
        this.camera = camera;
        this.endTurnSignal = endTurnSignal;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        Family family = Family
                .all(PositionComponent.class, DirectionComponent.class)
                .exclude(SpriterModelComponent.class)
                .get();
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        if (entities.size() != 1) {
            Gdx.app.error("InputSystem", "No controllable entity or more than one entity");
            Gdx.app.exit();
        }
        controlledEntity = entities.first();
        Gdx.input.setInputProcessor(new InputController());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector3 position = posMapper.get(controlledEntity).getPosition();
        Vector3 lookingDirection = directionMapper.get(controlledEntity).getDirection().value;
        camera.position.interpolate(position, 0.5f, Interpolation.linear);
        float alpha = 0.2f;
        if (!cameraLockOverride) camera.direction.lerp(lookingDirection, alpha);
        camera.update();
    }

    /**
     * Interprets player input. Changes the player's position, orientation and camera looking direction.
     */
    private class InputController extends InputAdapter {

        private final Vector3 rotationAx = new Vector3();
        private float degreesPerPixel = 0.000000000005f;
        private Vector3 unrestrictedDirection = new Vector3();

        private float currentYRotation = 0;
        private float maxYRotation = 89;
        private float minYRotation = -89;

        private float currentXRotation = 0;
        private float maxXRotation = 89;
        private float minXRotation = -89;
        private PositionComponent currentPosition;

        @Override
        public boolean keyDown(int keycode) {
            CardinalDirection direction = directionMapper.get(controlledEntity).getDirection();
            currentPosition = posMapper.get(controlledEntity);
            switch (keycode) {
                case Input.Keys.W:
                    moveIn(direction);
                    break;
                case Input.Keys.S:
                    moveIn(CardinalDirection.oppositeOf(direction));
                    break;
                case Input.Keys.A:
                    directionMapper.get(controlledEntity).setDirection(CardinalDirection.leftOf(direction));
                    break;
                case Input.Keys.D:
                    directionMapper.get(controlledEntity).setDirection(CardinalDirection.rightOf(direction));
                    break;
                case Input.Keys.SPACE:
                    Gdx.app.exit();
                    break;
                case Input.Keys.COMMA:
                    endTurnSignal.dispatch(InputSystem.this);
            }
            return true;
        }

        private void moveIn(CardinalDirection direction) {
            DungeonSection currentSection = currentPosition.getOccupiedSection();
            DungeonSection adjacentSection = currentSection;
            if (currentSection.getConnection(direction) != null) {
                adjacentSection = currentSection.getConnection(direction).getToNode();
            }
            if (adjacentSection.getOccupyingObjects().size() <= 0) {
                moveToSection(adjacentSection, currentPosition);
            }
        }

        private void moveToSection(DungeonSection section, PositionComponent currentPosition) {
            currentPosition.getOccupiedSection().getOccupyingObjects().remove(controlledEntity);
            currentPosition.setOccupiedSection(section);
            currentPosition.getPosition().set(section.getPosition().x, 6.5f, section.getPosition().z);
            section.addOccupyingObject(controlledEntity);
            endTurnSignal.dispatch(InputSystem.this);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 lookingDirection = directionMapper.get(controlledEntity).getDirection().value;
            float angle = (float) (MathUtils.radiansToDegrees * Math.acos(unrestrictedDirection.dot(lookingDirection)
                    / (unrestrictedDirection.len() * lookingDirection.len() + 0.00000000001f)));

            degreesPerPixel = (float) (0.851763 / (1.758 * Math.pow(10, -69) * Math.pow(angle + 4, 35) + 1) - 0.351763); // makes the speed of camera rotation have a smooth end

            float deltaX = (-Gdx.input.getDeltaX() * degreesPerPixel);
            float deltaY = (-Gdx.input.getDeltaY() * degreesPerPixel);

            if (deltaY > 0 && currentYRotation < maxYRotation) {
                currentYRotation += deltaY;
            } else if (deltaY < 0 && currentYRotation > minYRotation) {
                currentYRotation += deltaY;
            } else deltaY = 0;
            if (deltaX > 0 && currentXRotation < maxXRotation) {
                currentXRotation += deltaX;
            } else if (deltaX < 0 && currentXRotation > minXRotation) {
                currentXRotation += deltaX;
            } else deltaX = 0;
            // TODO: 21.08.17 limit camera rotation or/and make the speed of snap in to default position smoothly

            unrestrictedDirection = unrestrictedDirection.set(camera.direction);
            unrestrictedDirection.rotate(camera.up, deltaX);
            rotationAx.set(unrestrictedDirection).crs(camera.up).nor();
            unrestrictedDirection.rotate(rotationAx, deltaY);
            camera.direction.set(unrestrictedDirection);

            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            cameraLockOverride = true;
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            cameraLockOverride = false;
            currentYRotation = 0;
            currentXRotation = 0;
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            return super.keyUp(keycode);
        }
    }

}
