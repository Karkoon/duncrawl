package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class FloatingSpeedComponent implements Component, Pool.Poolable {

  private float speed;

  @Override
  public void reset() {
    speed = 0;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }
}
