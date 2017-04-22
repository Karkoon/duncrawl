package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 25.03.17.
 */
public final class SpeedComponent implements Component {

    private Vector3 moveRate;

    public Vector3 getMoveRate() {
        return moveRate;
    }

    public void setMoveRate(Vector3 moveRate) {
        this.moveRate = moveRate;
    }
}
