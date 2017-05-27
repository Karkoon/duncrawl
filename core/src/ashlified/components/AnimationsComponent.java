package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;

/**
 * Created by karkoon on 25.03.17.
 */
public final class AnimationsComponent implements Component, Pool.Poolable {

    private Player player;
    private Loader loader;
    private Drawer drawer;

    public Drawer getDrawer() {
        return drawer;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void reset() {
        player = null;
        loader = null;
        drawer = null;
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
