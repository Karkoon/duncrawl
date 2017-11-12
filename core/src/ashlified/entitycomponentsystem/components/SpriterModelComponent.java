package ashlified.entitycomponentsystem.components;

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

    public static class SpriterAnimationController {

        private Player player;
        private Queue<Runnable> tasks = new Queue<>();

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(final Player player) {
            this.player = player;
            this.player.addListener(new Player.PlayerListener() {
                @Override
                public void animationFinished(Animation animation) {
                    if (tasks.size > 0) {
                        tasks.removeFirst().run();
                    } else {
                        player.setAnimation(AnimationState.IDLE.getId());
                    }
                }

                @Override
                public void animationChanged(Animation animation, Animation animation1) {

                }

                @Override
                public void preProcess(Player player) {

                }

                @Override
                public void postProcess(Player player) {

                }

                @Override
                public void mainlineKeyChanged(Mainline.Key key, Mainline.Key key1) {

                }
            });
        }

        public void attackEvent() {
            animationEvent(AnimationState.ATTACK);
        }

        public void dieEvent() {
            animationEvent(AnimationState.DIE);

        }

        public void damagedEvent() {
            animationEvent(AnimationState.DAMAGED);
        }

        private void animationEvent(final AnimationState state) {
            if (player.getAnimation().id == AnimationState.IDLE.getId()) {
                player.setAnimation(state.getId());
            } else {
                tasks.addLast(new Runnable() {
                    @Override
                    public void run() {
                        player.setAnimation(state.getId());
                    }
                });
            }
        }
    }
}
