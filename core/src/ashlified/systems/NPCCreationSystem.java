package ashlified.systems;

import ashlified.dungeon.Dungeon;
import ashlified.factories.NPCFactory;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCCreationSystem extends EntitySystem {

    private Dungeon dungeon;
    private AssetManager assetManager;
    private NPCFactory factory;

    public NPCCreationSystem(Dungeon dungeon, AssetManager assetManager) {
        this.dungeon = dungeon;
        this.assetManager = assetManager;
    }

    private void createInitialNPCs() {
        factory = new NPCFactory(getEngine(), assetManager);
        for (int i = 0; i < 100; i++) {
            factory.createEnemyNPC("Hell knight", dungeon);
            factory.createEnemyNPC("Snorg", dungeon);
            factory.createEnemyNPC("Ghost", dungeon);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createInitialNPCs();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        factory = null;
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
