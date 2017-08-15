package ashlified.systems;

import ashlified.AssetPaths;
import ashlified.components.PositionComponent;
import ashlified.components.SpriterModelComponent;
import ashlified.graphics.ModelInstanceRenderer;
import ashlified.graphics.spriterutils.PoolPlaneAtlasDrawer;
import ashlified.loading.assetmanagerloaders.SCMLDataWithResourcesLoader;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Loader;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCRenderingSystem extends IteratingSystem {

    private ComponentMapper<SpriterModelComponent> graphicsMapper = ComponentMapper.getFor(SpriterModelComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    private PoolPlaneAtlasDrawer npcDrawer;

    public NPCRenderingSystem(AssetManager manager, ModelInstanceRenderer renderer) {
        super(Family.all(SpriterModelComponent.class, PositionComponent.class).get());
        Loader loader = manager.get(AssetPaths.SCML_FILE, SCMLDataWithResourcesLoader.SCMLDataWithResources.class).getLoader();
        npcDrawer = new PoolPlaneAtlasDrawer(loader, renderer);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriterModelComponent animComp = graphicsMapper.get(entity);
        PositionComponent posComp = positionMapper.get(entity);
        updateAnimationPosition(animComp, posComp.getPosition());
        updateAnimationTime(animComp);
        npcDrawer.draw(animComp.getPlayer());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        npcDrawer.flush();
    }

    private void updateAnimationPosition(SpriterModelComponent animComp, Vector3 position) {
        animComp.getPlayer().setPosition(position.x, position.z);
    }

    private void updateAnimationTime(SpriterModelComponent animation) {
        animation.getPlayer().update();
    }
}
