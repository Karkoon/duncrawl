package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.Dungeon;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by karkoon on 25.03.17.
 */
public class NpcCreationSystem extends EntitySystem {

    private Dungeon dungeon;
    private AssetManager assetManager;
    private ashlified.entitycomponentsystem.entityinitializers.EnemyNPCEntitiesInitializer factory;

    public NpcCreationSystem(Dungeon dungeon, AssetManager assetManager) {
        this.dungeon = dungeon;
        this.assetManager = assetManager;
    }

    private void createInitialNPCs() {
        for (int i = 0; i < 30; i++) {
            factory.addEnemyNPC("Hell knight", dungeon);
            factory.addEnemyNPC("Snorg", dungeon);
            factory.addEnemyNPC("Ghost", dungeon);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = new ashlified.entitycomponentsystem.entityinitializers.EnemyNPCEntitiesInitializer(getEngine(), assetManager);
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
