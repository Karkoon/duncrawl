package ashlified.graphics.spriterutils;

import com.badlogic.gdx.utils.Queue;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Player;

public class SpriterAnimationController {

    private Player player;
    private Queue<Runnable> tasks = new Queue<>();
    private boolean locked = false;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
        this.player.addListener(new PlayerListenerAdapter() {
            @Override
            public void animationFinished(Animation animation) {
                if (tasks.size > 0) {
                    tasks.removeFirst().run();
                } else if (animation.id == AnimationID.DIE.getId()) {
                    locked = false;
                } else {
                    player.setAnimation(AnimationID.IDLE.getId());
                    locked = false;
                }
            }
        });
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