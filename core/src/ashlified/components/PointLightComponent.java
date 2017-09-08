package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 13.05.17.
 */
public class PointLightComponent implements Component, Pool.Poolable {

    private PointLight pointLight;

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    @Override
    public void reset() {
        pointLight = null;
    }
}
