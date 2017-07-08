package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.brashmonkey.spriter.Player;

/**
 * Created by karkoon on 25.03.17.
 */
public final class GraphicalComponent implements Component, Pool.Poolable {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void reset() {
        player = null;
    }

    public enum AnimationState {
        IDLE(0), ATTACK(1), DIE(2), DAMAGED(3);

        private int id;

        AnimationState(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
