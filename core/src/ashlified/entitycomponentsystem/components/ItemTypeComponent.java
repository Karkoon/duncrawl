package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class ItemTypeComponent implements Component, Pool.Poolable {

  private Type type;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public void reset() {
    type = null;
  }

  public enum Type {
    ARMOR, BOOT, GLOVE, HELMET, PENDANT, RING, RUBBISH, TROUSERS
  }

}
