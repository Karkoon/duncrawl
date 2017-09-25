package ashlified.inputprocessors;

import ashlified.entitycomponentsystem.components.LookingDirectionComponent;
import ashlified.entitycomponentsystem.components.MovingDirectionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

public class TouchInput extends InputAdapter {

    private final Vector3 rotationAxis = new Vector3();
    private Vector3 unrestrictedLookingDirection = new Vector3();
    private float currentYRotation = 0;
    private float currentXRotation = 0;
    private MovingDirectionComponent movDir;
    private LookingDirectionComponent lookDir;

    public TouchInput(Entity controlledEntity) {
        ComponentMapper<MovingDirectionComponent> movingDirectionMapper = ComponentMapper.getFor(MovingDirectionComponent.class);
        ComponentMapper<LookingDirectionComponent> lookingDirectionMapper = ComponentMapper.getFor(LookingDirectionComponent.class);
        movDir = movingDirectionMapper.get(controlledEntity);
        lookDir = lookingDirectionMapper.get(controlledEntity);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        float degreesPerPixel = 0.3f;
        float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
        float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;

        float maxYRotation = 60;
        float minYRotation = -60;
        if ((deltaY > 0 && currentYRotation < maxYRotation)
                || (deltaY < 0 && currentYRotation > minYRotation)) {
            currentYRotation += deltaY;
        } else {
            deltaY = 0;
        }

        float maxXRotation = 89;
        float minXRotation = -89;
        if ((deltaX > 0 && currentXRotation < maxXRotation)
                || (deltaX < 0 && currentXRotation > minXRotation)) {
            currentXRotation += deltaX;
        } else {
            deltaX = 0;
        }

        Vector3 lookingDirection = lookDir.getLookingDirection();
        unrestrictedLookingDirection = unrestrictedLookingDirection.set(lookingDirection);
        unrestrictedLookingDirection.rotate(Vector3.Y, deltaX);
        rotationAxis.set(unrestrictedLookingDirection).crs(Vector3.Y).nor();
        unrestrictedLookingDirection.rotate(rotationAxis, deltaY);
        lookingDirection.set(unrestrictedLookingDirection);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        currentYRotation = 0;
        currentXRotation = 0;
        lookDir.getLookingDirection().set(movDir.getDirection().value);
        return true;
    }
}
