package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class LookingDirectionComponent implements Component, Pool.Poolable {

    private Vector3 lookingDirection;

    public Vector3 getLookingDirection() {
        return lookingDirection;
    }

    public void setLookingDirection(Vector3 lookingDirection) {
        this.lookingDirection = lookingDirection;
    }

    @Override
    public void reset() {
        lookingDirection = null;
    }
}
