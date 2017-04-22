package ashlified.systems;

import ashlified.components.AnimationsComponent;
import ashlified.components.DecalComponent;
import ashlified.components.PositionComponent;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by karkoon on 22.04.17.
 */
public class RenderingSystem extends EntitySystem {

    private DroppedItemsRenderingSystem droppedItemsRenderingSystem;
    private NPCRenderingSystem NPCRenderingSystem;

    private DecalBatch decalBatch;
    private Viewport viewport;

    public RenderingSystem() {
        setUpViewport();
        decalBatch = new DecalBatch(new CameraGroupStrategy(viewport.getCamera()));
        Family NPCs = Family.all(AnimationsComponent.class, PositionComponent.class).get();
        NPCRenderingSystem = new NPCRenderingSystem(NPCs, decalBatch);
        Family droppedItems = Family.all(DecalComponent.class, PositionComponent.class).get();
        droppedItemsRenderingSystem = new DroppedItemsRenderingSystem(droppedItems, decalBatch);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        droppedItemsRenderingSystem.update(deltaTime);
        NPCRenderingSystem.update(deltaTime);
        drawDecals();
    }

    private void setUpViewport() {
        PerspectiveCamera camera = new PerspectiveCamera(67, 300, 300);
        camera.near = 1f;
        camera.far = 1000f;
        viewport = new ExtendViewport(300, 300, camera);
    }

    private void drawDecals() {
        decalBatch.flush();
    }

}
