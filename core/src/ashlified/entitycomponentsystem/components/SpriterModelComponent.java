package ashlified.entitycomponentsystem.components;

import ashlified.graphics.spriterutils.SpriterAnimationController;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

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
}
