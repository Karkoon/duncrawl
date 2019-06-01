package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class StatsComponent implements Component, Pool.Poolable {

  private int strength;
  private int dexterity;
  private int wisdom;

  public int getStrength() {
    return strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int getDexterity() {
    return dexterity;
  }

  public void setDexterity(int dexterity) {
    this.dexterity = dexterity;
  }

  public int getWisdom() {
    return wisdom;
  }

  public void setWisdom(int wisdom) {
    this.wisdom = wisdom;
  }

  @Override
  public void reset() {
    strength = 0;
    dexterity = 0;
    wisdom = 0;
  }
}
