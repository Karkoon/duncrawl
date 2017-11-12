package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.PointLightComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

/**
 * Updates light sources positions.
 */
public class LightingSystem extends IteratingSystem {

    private ComponentMapper<PointLightComponent> lightMapper = ComponentMapper.getFor(PointLightComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    private float lightPositionChangeAlpha = 0.15f;

    public LightingSystem() {
        super(Family.all(PointLightComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PointLight light = lightMapper.get(entity).getPointLight();
        Vector3 pos = posMapper.get(entity).getPosition();
        light.position.interpolate(pos, lightPositionChangeAlpha, Interpolation.linear);
    }
}
