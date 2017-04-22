package ashlified.systems;

import ashlified.Graphics;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Created by karkoon on 25.03.17.
 */
public class RenderingSystem extends IteratingSystem {

    private final Graphics renderer;

    ComponentMapper mapper = new ComponentMapper<>()

    public RenderingSystem(Family family, Graphics renderer) {
        super(family);
        this.renderer = renderer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderer.render(entity);
    }
}
