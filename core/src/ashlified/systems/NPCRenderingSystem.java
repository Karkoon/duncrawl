package ashlified.systems;

import ashlified.ModelInstanceRenderer;
import ashlified.assetManager.SCMLDataAndResources;
import ashlified.components.GraphicalComponent;
import ashlified.components.PositionComponent;
import ashlified.spriterutils.PlaneDrawer;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Loader;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCRenderingSystem extends IteratingSystem {

    private ComponentMapper<GraphicalComponent> graphicsMapper = ComponentMapper.getFor(GraphicalComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    private PlaneDrawer npcDrawer;

    public NPCRenderingSystem(Family family, AssetManager manager, ModelInstanceRenderer renderer) {
        super(family);
        Loader loader = manager.get("npc/backup.scml", SCMLDataAndResources.class).getLoader();
        npcDrawer = new PlaneDrawer(loader, renderer);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        GraphicalComponent animComp = graphicsMapper.get(entity);
        PositionComponent posComp = positionMapper.get(entity);
        updateAnimationPosition(animComp, posComp.getPosition());
        updateAnimationTime(animComp, deltaTime);
        Gdx.app.log("NPCRenderingSystem", "rendering one entity");
        npcDrawer.draw(animComp.getPlayer());
    }

    private void updateAnimationPosition(GraphicalComponent animComp, Vector3 position) {
        animComp.getPlayer().setPosition(position.x, position.z);
    }

    private void updateAnimationTime(GraphicalComponent animation, float deltaTime) {
        animation.getPlayer().update();
    }
}
