package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Pool;

public class ModelInstanceComponent implements Component, Pool.Poolable {

    private ModelInstance instance;

    public ModelInstance getInstance() {
        return instance;
    }

    public void setInstance(ModelInstance instance) {
        this.instance = instance;
    }

    @Override
    public void reset() {
        instance = null;
    }
}
