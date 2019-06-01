package ashlified.entitycomponentsystem.entitysystems;

import ashlified.AssetPaths;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpriterModelComponent;
import ashlified.graphics.ModelInstanceRenderer;
import ashlified.graphics.spriterutils.PoolPlaneAtlasLoader;
import ashlified.graphics.spriterutils.PooledPlaneAtlasDrawer;
import ashlified.loading.assetmanagerloaders.ScmlDataWithResourcesLoader;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 25.03.17.
 */
public class FlatImagesDrawingSystem extends IteratingSystem {

  private ComponentMapper<SpriterModelComponent> graphicsMapper = ComponentMapper.getFor(SpriterModelComponent.class);
  private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

  private PooledPlaneAtlasDrawer poolPlaneAtlasDrawer;

  FlatImagesDrawingSystem(AssetManager manager, ModelInstanceRenderer renderer) {
    super(Family.all(SpriterModelComponent.class, PositionComponent.class).get());
    PoolPlaneAtlasLoader loader = (PoolPlaneAtlasLoader) manager.get(AssetPaths.SCML_FILE, ScmlDataWithResourcesLoader.SCMLDataWithResources.class).getLoader();
    poolPlaneAtlasDrawer = new PooledPlaneAtlasDrawer(loader, renderer);
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    SpriterModelComponent animComp = graphicsMapper.get(entity);
    PositionComponent posComp = positionMapper.get(entity);
    updateAnimationPosition(animComp, posComp.getPosition());
    updateAnimationTime(animComp);
    poolPlaneAtlasDrawer.draw(animComp.getSpriterAnimationController().getPlayer());
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    poolPlaneAtlasDrawer.flush();
  }

  private void updateAnimationPosition(SpriterModelComponent animComp, Vector3 position) {
    animComp.getSpriterAnimationController().getPlayer().setPosition(position.x, position.z);
  }

  private void updateAnimationTime(SpriterModelComponent animation) {
    animation.getSpriterAnimationController().getPlayer().update();
  }
}
