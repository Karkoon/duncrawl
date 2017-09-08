package ashlified.systems;

import ashlified.components.PointLightComponent;
import ashlified.components.PositionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public class LightingSystem extends IteratingSystem {

    private ComponentMapper<PointLightComponent> lightMapper = ComponentMapper.getFor(PointLightComponent.class);
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

    public LightingSystem() {
        super(Family.all(PointLightComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PointLight light = lightMapper.get(entity).getPointLight();
        Vector3 pos = posMapper.get(entity).getPosition();
        light.position.interpolate(pos, 0.5f, Interpolation.linear);
    }
}
