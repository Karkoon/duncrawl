package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class ItemStateComponent implements Component {

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    enum State {
        DROPPED, NOT_DROPPED
    }

    private State state;
}
