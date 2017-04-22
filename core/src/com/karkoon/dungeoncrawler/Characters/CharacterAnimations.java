package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.karkoon.dungeoncrawler.Interfaces.Drawable;
import com.karkoon.dungeoncrawler.Interfaces.Updateable;

/**
 * Created by karkoon on 01.02.17.
 */
public class CharacterAnimations implements Updateable, Drawable {

    enum State {
        IDLE, ATTACK, DAMAGE, KILL
    }

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attack;
    private Animation<TextureRegion> damage;
    private Animation<TextureRegion> kill;
    private Animation<TextureRegion> currentAnimation;
    private Decal decal;

    private float stateTime = 0;

    CharacterAnimations(Array<TextureRegion> idleFrames, Array<TextureRegion> attackFrames,
                        Array<TextureRegion> damageFrames, Array<TextureRegion> killFrames) {
        float width = 10;
        float height = 10;
        float frameDuration = 0.5f;
        this.decal = Decal.newDecal(width, height, idleFrames.first(), true);
        this.idle = new Animation<>(frameDuration, idleFrames, Animation.PlayMode.LOOP);
        this.attack = new Animation<>(frameDuration, attackFrames, Animation.PlayMode.LOOP);
        this.damage = new Animation<>(frameDuration, damageFrames, Animation.PlayMode.LOOP);
        this.kill = new Animation<>(frameDuration, killFrames, Animation.PlayMode.LOOP);
    }

    public void changeState(State state) {
        switch (state) {
            case IDLE:
                currentAnimation = idle;
                break;
            case ATTACK:
                currentAnimation = attack;
                break;
            case DAMAGE:
                currentAnimation = damage;
                break;
            case KILL:
                currentAnimation = kill;
                break;
        }
        stateTime = 0;
    }

    public void setPosition(Vector3 position) {
        decal.getPosition().lerp(position, 0.1f);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
        decal.setTextureRegion(currentAnimation.getKeyFrame(stateTime));
        decal.lookAt(lookAt, Vector3.Y);
        batch.add(decal);
    }
}
