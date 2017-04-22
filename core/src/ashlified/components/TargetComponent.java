package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * Created by karkoon on 22.04.17.
 */
public final class TargetComponent implements Component {

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    private Entity target;

}
