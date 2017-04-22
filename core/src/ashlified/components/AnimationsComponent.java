package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import java.util.EnumMap;

/**
 * Created by karkoon on 25.03.17.
 */
public final class AnimationsComponent implements Component {

    private float time = 0;
    private EnumMap<AnimationState, Animation<Decal>> animations
            = new EnumMap<>(AnimationState.class);

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public EnumMap<AnimationState, Animation<Decal>> getAnimations() {
        return animations;
    }

    public void setAnimations(EnumMap<AnimationState, Animation<Decal>> animations) {
        this.animations = animations;
    }

    public enum AnimationState {
        IDLE, ATTACK, DIE, DAMAGED
    }

}
