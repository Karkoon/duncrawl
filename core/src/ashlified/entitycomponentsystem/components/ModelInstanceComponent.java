package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Pool;

public class ModelInstanceComponent implements Component, Pool.Poolable {

  private ModelInstance modelInstance;

  public ModelInstance getModelInstance() {
    return modelInstance;
  }

  public void setModelInstance(ModelInstance modelInstance) {
    this.modelInstance = modelInstance;
  }

  @Override
  public void reset() {
    modelInstance = null;
  }
}
