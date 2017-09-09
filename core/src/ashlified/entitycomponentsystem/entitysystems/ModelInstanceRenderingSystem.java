package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.ModelInstanceComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.graphics.ModelInstanceRenderer;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Renders model instances.
 */
public class ModelInstanceRenderingSystem extends IteratingSystem {

    private ComponentMapper<ModelInstanceComponent> modelInstanceMapper = ComponentMapper.getFor(ModelInstanceComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ModelInstanceRenderer renderer;

    public ModelInstanceRenderingSystem(ModelInstanceRenderer renderer) {
        super(Family.all(ModelInstanceComponent.class, PositionComponent.class).get());
        this.renderer = renderer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ModelInstanceComponent modComp = modelInstanceMapper.get(entity);
        PositionComponent posComp = positionMapper.get(entity);
        modComp.getInstance().transform.setTranslation(posComp.getPosition().x, 0, posComp.getPosition().z);
        renderer.addToCache(modComp.getInstance());
    }

}
