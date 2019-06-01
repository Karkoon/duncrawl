package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.Pool;

public class AnimationControllerComponent implements Component, Pool.Poolable {

  private AnimationController controller;

  public AnimationController getController() {
    return controller;
  }

  public void setController(AnimationController controller) {
    this.controller = controller;
  }

  @Override
  public void reset() {
    controller = null;
  }
}
