package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;

/**
 * Created by karkoon on 25.03.17.
 */
public final class AnimationsComponent implements Component {

    private Player player;
    private Loader<Decal> loader;

    public Loader<Decal> getLoader() {
        return loader;
    }

    public void setLoader(Loader<Decal> loader) {
        this.loader = loader;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
