package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.Dungeon;
import ashlified.graphics.ModelInstanceRenderer;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;

public class EntitySystems {

  private final FlatImagesDrawingSystem npcRenderingSystem;
  private final ModelInstanceRenderingSystem modelInstanceRenderingSystem;
  private final ModelAnimationSystem modelAnimationSystem;
  private final LightingSystem lightingSystem;
  private final NpcAiSystem npcAiSystem;
  private final TargetingSystem targetSystem;
  private final CameraUpdateSystem cameraUpdateSystem;
  private final DeadEntitiesRemoverSystem deadEntitiesRemoverSystem;
  private final PositionSystem positionSystem;
  private final AttackSystem attackSystem;

  private final ItemRendererSystem itemRendererSystem;
  private final PickUpSystem pickUpSystem;

  public EntitySystems(AssetManager assetManager, ModelInstanceRenderer renderer, Dungeon dungeon, Camera camera) {
    this.deadEntitiesRemoverSystem = new DeadEntitiesRemoverSystem();
    this.attackSystem = new AttackSystem();
    this.npcRenderingSystem = new FlatImagesDrawingSystem(assetManager, renderer);
    this.modelInstanceRenderingSystem = new ModelInstanceRenderingSystem(renderer);
    this.modelAnimationSystem = new ModelAnimationSystem();
    this.lightingSystem = new LightingSystem();
    this.npcAiSystem = new NpcAiSystem(dungeon);
    this.targetSystem = new TargetingSystem();
    this.cameraUpdateSystem = new CameraUpdateSystem(camera);
    this.positionSystem = new PositionSystem();
    this.itemRendererSystem = new ItemRendererSystem(renderer);
    this.pickUpSystem = new PickUpSystem();
  }

  public void addSystemsTo(Engine engine) {
    engine.addSystem(itemRendererSystem);
    engine.addSystem(attackSystem);
    engine.addSystem(npcRenderingSystem);
    engine.addSystem(modelAnimationSystem);
    engine.addSystem(modelInstanceRenderingSystem);
    engine.addSystem(deadEntitiesRemoverSystem);
    engine.addSystem(lightingSystem);
    engine.addSystem(targetSystem);
    engine.addSystem(npcAiSystem);
    engine.addSystem(cameraUpdateSystem);
    engine.addSystem(positionSystem);
    engine.addSystem(pickUpSystem);
  }

  public PickUpSystem getPickUpSystem() {
    return pickUpSystem;
  }

  public ItemRendererSystem getItemRendererSystem() {
    return itemRendererSystem;
  }

  public PositionSystem getPositionSystem() {
    return positionSystem;
  }

  public AttackSystem getAttackSystem() {
    return attackSystem;
  }

  public DeadEntitiesRemoverSystem getDeadEntitiesRemoverSystem() {
    return deadEntitiesRemoverSystem;
  }

  public FlatImagesDrawingSystem getNpcRenderingSystem() {
    return npcRenderingSystem;
  }

  public ModelInstanceRenderingSystem getModelInstanceRenderingSystem() {
    return modelInstanceRenderingSystem;
  }

  public ModelAnimationSystem getModelAnimationSystem() {
    return modelAnimationSystem;
  }

  public LightingSystem getLightingSystem() {
    return lightingSystem;
  }

  public NpcAiSystem getNpcAiSystem() {
    return npcAiSystem;
  }

  public TargetingSystem getTargetSystem() {
    return targetSystem;
  }

  public CameraUpdateSystem getCameraUpdateSystem() {
    return cameraUpdateSystem;
  }
}
