package ashlified.entitylisteners;

import ashlified.components.PointLightComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g3d.Environment;

public class LightComponentListener implements EntityListener {

    private Environment environment;
    private ComponentMapper<PointLightComponent> lightMapper = ComponentMapper.getFor(PointLightComponent.class);

    public LightComponentListener(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void entityAdded(Entity entity) {
        environment.add(lightMapper.get(entity).getPointLight());
    }

    @Override
    public void entityRemoved(Entity entity) {
        environment.remove(lightMapper.get(entity).getPointLight());
    }
}
