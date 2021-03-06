package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class HealthComponent extends Signal<HealthComponent> implements Component, Pool.Poolable {

  private int health;
  private int maxHealth;

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    dispatch(this);
    this.health = health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
    dispatch(this);
  }

  @Override
  public void reset() {
    health = 0;
    maxHealth = 0;
  }
}
