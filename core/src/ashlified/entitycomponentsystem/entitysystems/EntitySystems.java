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
    private final DeadEntitiesRemoverSystem deadEntitiesRemover;

    public EntitySystems(AssetManager assetManager, ModelInstanceRenderer renderer, Dungeon dungeon, Camera camera) {
        this.npcRenderingSystem = new FlatImagesDrawingSystem(assetManager, renderer);
        this.modelInstanceRenderingSystem = new ModelInstanceRenderingSystem(renderer);
        this.modelAnimationSystem = new ModelAnimationSystem();
        this.lightingSystem = new LightingSystem();
        this.npcAiSystem = new NpcAiSystem(dungeon);
        this.targetSystem = new TargetingSystem();
        this.cameraUpdateSystem = new CameraUpdateSystem(camera);
        this.deadEntitiesRemover = new DeadEntitiesRemoverSystem();
    }

    public void addSystemsTo(Engine engine) {
        engine.addSystem(npcRenderingSystem);
        engine.addSystem(modelAnimationSystem);
        engine.addSystem(modelInstanceRenderingSystem);
        engine.addSystem(lightingSystem);
        engine.addSystem(targetSystem);
        engine.addSystem(npcAiSystem);
        engine.addSystem(cameraUpdateSystem);
        engine.addSystem(deadEntitiesRemover);
    }

    public DeadEntitiesRemoverSystem getDeadEntitiesRemover() {
        return deadEntitiesRemover;
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
