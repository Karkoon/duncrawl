package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class AttackActionComponent implements Component, Pool.Poolable {

  private Runnable reaction;
  private Runnable action;

  @Override
  public void reset() {
    reaction = null;
    action = null;
  }

  public Runnable getReaction() {
    return reaction;
  }

  public void setReaction(Runnable reaction) {
    this.reaction = reaction;
  }

  public Runnable getAction() {
    return action;
  }

  public void setAction(Runnable action) {
    this.action = action;
  }
}
