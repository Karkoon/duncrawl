package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.Dungeon;
import ashlified.graphics.Graphics;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;

public class EntitySystems {

    private final NpcRenderingSystem npcRenderingSystem;
    private final ModelInstanceRenderingSystem modelInstanceRenderingSystem;
    private final ModelAnimationSystem modelAnimationSystem;
    private final LightingSystem lightingSystem;
    private final NpcAiSystem npcAiSystem;
    private final TargetingSystem targetSystem;
    private final CameraUpdateSystem cameraUpdateSystem;

    public EntitySystems(AssetManager assetManager, Graphics graphics, Dungeon dungeon) {
        this.npcRenderingSystem = new NpcRenderingSystem(assetManager, graphics.getModelInstanceRenderer());
        this.modelInstanceRenderingSystem = new ModelInstanceRenderingSystem(graphics.getModelInstanceRenderer());
        this.modelAnimationSystem = new ModelAnimationSystem();
        this.lightingSystem = new LightingSystem();
        this.npcAiSystem = new NpcAiSystem(dungeon);
        this.targetSystem = new TargetingSystem();
        this.cameraUpdateSystem = new CameraUpdateSystem(graphics.getCamera());
    }

    public void addSystemsTo(Engine engine) {
        engine.addSystem(npcRenderingSystem);
        engine.addSystem(modelAnimationSystem);
        engine.addSystem(modelInstanceRenderingSystem);
        engine.addSystem(lightingSystem);
        engine.addSystem(npcAiSystem);
        engine.addSystem(targetSystem);
        engine.addSystem(cameraUpdateSystem);
    }

    public NpcRenderingSystem getNpcRenderingSystem() {
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
