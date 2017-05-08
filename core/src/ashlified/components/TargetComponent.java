package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 22.04.17.
 */
public final class TargetComponent implements Component, Pool.Poolable {

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    private Entity target;

    @Override
    public void reset() {
        target = null;
    }
}
