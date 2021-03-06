package ashlified.loading;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Mats Svensson
 * https://github.com/Matsemann/libgdx-loading-screen
 */
class LoadingBar extends Actor {

  private Animation animation;
  private TextureRegion reg;
  private float stateTime;

  LoadingBar(Animation animation) {
    this.animation = animation;
    reg = (TextureRegion) animation.getKeyFrame(0);
  }

  @Override
  public void act(float delta) {
    stateTime += delta;
    reg = (TextureRegion) animation.getKeyFrame(stateTime);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(reg, getX(), getY());
  }
}