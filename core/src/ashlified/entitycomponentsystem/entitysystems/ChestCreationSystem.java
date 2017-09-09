package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.Dungeon;
import ashlified.entitycomponentsystem.entityinitializers.ChestEntityInitializer;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Creates a set of chest randomly scattered in the dungeon.
 * (It shouldn't really be in its own system but I've written it that way.
 * todo change it later
 */
public class ChestCreationSystem extends EntitySystem {

    private Dungeon dungeon;
    private AssetManager assetManager;
    private ChestEntityInitializer chestFactory;

    public ChestCreationSystem(Dungeon dungeon, AssetManager assetManager) {
        this.dungeon = dungeon;
        this.assetManager = assetManager;
    }

    private void createInitialChests() {
        for (int i = 0; i < 30; i++) {
            chestFactory.addChest(dungeon);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.chestFactory = new ChestEntityInitializer(engine, assetManager);
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
