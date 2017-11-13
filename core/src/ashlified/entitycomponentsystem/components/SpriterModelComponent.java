package ashlified.entitycomponentsystem.components;

import ashlified.graphics.spriterutils.PlayerListenerAdapter;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Queue;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;

/**
 * Created by karkoon on 25.03.17.
 */
public final class SpriterModelComponent implements Component, Pool.Poolable {

    private SpriterAnimationController spriterAnimationController;

    public SpriterAnimationController getSpriterAnimationController() {
        return spriterAnimationController;
    }

    public void setSpriterAnimationController(SpriterAnimationController spriterAnimationController) {
        this.spriterAnimationController = spriterAnimationController;
    }

    @Override
    public void reset() {
        spriterAnimationController = null;
    }

    public enum AnimationID {
        IDLE(0), ATTACK(1), DIE(2), DAMAGED(3);

        private int id;

        AnimationID(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class SpriterAnimationController {

        private Player player;
        private Queue<Runnable> tasks = new Queue<>();

        public Player getPlayer() {
            return player;
        }
        private boolean locked = false;

        public void setPlayer(final Player player) {
            this.player = player;
            this.player.addListener(new PlayerListenerAdapter() {
                @Override
                public void animationFinished(Animation animation) {
                    if (tasks.size > 0) {
                        tasks.removeFirst().run();
                    } else {
                        player.setAnimation(AnimationID.IDLE.getId());
                        locked = false;
                    }
                }});
        }

        public void attackEvent() {
            animationEvent(AnimationID.ATTACK);
        }

        public void dieEvent() {
            animationEvent(AnimationID.DIE);
        }

        public void damagedEvent() {
            animationEvent(AnimationID.DAMAGED);
        }

        private void animationEvent(final AnimationID state) {
            if (player.getAnimation().id == AnimationID.IDLE.getId()) {
                player.setAnimation(state.getId());
                locked = true;
            } else {
                tasks.addLast(new Runnable() {
                    @Override
                    public void run() {
                        player.setAnimation(state.getId());
                    }
                });
            }
        }

        public boolean isLocked() {
            return locked;
        }
    }
}
