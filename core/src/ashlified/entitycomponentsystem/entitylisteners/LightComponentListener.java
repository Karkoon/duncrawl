package ashlified.entitycomponentsystem.entitylisteners;

import ashlified.entitycomponentsystem.components.PointLightComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g3d.Environment;

/**
 * Listens for entities which add a new light source.
 * Then adds the light source to the lighting engine.
 */
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
