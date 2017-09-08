package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class ItemStateComponent implements Component, Pool.Poolable {

    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void reset() {
        state = null;
    }

    enum State {
        DROPPED, NOT_DROPPED
    }
}
