package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class PickUpIntentComponent implements Component, Pool.Poolable {

  private Entity item;

  public Entity getItem() {
    return item;
  }

  public void setItem(Entity item) {
    this.item = item;
  }

  @Override
  public void reset() {
    this.item = null;
  }
}
