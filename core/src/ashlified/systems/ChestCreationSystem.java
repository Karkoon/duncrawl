package ashlified.systems;

import ashlified.dungeon.Dungeon;
import ashlified.systems.factories.ChestFactory;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;

public class ChestCreationSystem extends EntitySystem {

    private Dungeon dungeon;
    private AssetManager assetManager;
    private ChestFactory chestFactory;

    public ChestCreationSystem(Dungeon dungeon, AssetManager assetManager) {
        this.dungeon = dungeon;
        this.assetManager = assetManager;
    }

    private void createInitialChests() {
        for (int i = 0; i < 30; i++) {
            chestFactory.createChest(dungeon);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.chestFactory = new ChestFactory(engine, assetManager);
        createInitialChests();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing();
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
    }

    @Override
    public Engine getEngine() {
        return super.getEngine();
    }
}
