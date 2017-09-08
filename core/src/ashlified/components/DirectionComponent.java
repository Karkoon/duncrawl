package ashlified.components;

import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class DirectionComponent implements Pool.Poolable, Component {

    private CardinalDirection direction;

    public CardinalDirection getDirection() {
        return direction;
    }

    public void setDirection(CardinalDirection direction) {
        this.direction = direction;
    }

    @Override
    public void reset() {
        direction = null;
    }
}
