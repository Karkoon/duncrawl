package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.dungeon.Dungeon;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Creates the initial set of entities
 * todo Allows the creation of entities during playing
 */
public class GameEntities {

    private PooledEngine engine;
    private Dungeon dungeon;
    private AssetManager assetManager;

    public GameEntities(PooledEngine engine, Dungeon dungeon, AssetManager assetManager) {
        this.dungeon = dungeon;
        this.engine = engine;
        this.assetManager = assetManager;
    }

    public void createInitialEntities() {
        createPlayer();
        createInitialEnemyNPCs();
        createChests();
    }

    private void createPlayer() {
        new PlayerEntityConfigurer(engine).getPlayer(dungeon.getSpawnDungeonSection());
    }

    private void createInitialEnemyNPCs() {
        EnemyNPCEntityConfigurer enemyNPC = new EnemyNPCEntityConfigurer(engine, assetManager);
        for (int i = 0; i < 30; i++) {
            for (EnemyNPCEntityConfigurer.EnemyName enemyName : EnemyNPCEntityConfigurer.EnemyName.values()) {
                enemyNPC.addEnemyNPC(enemyName.getValue(), dungeon);
            }
        }
    }

    private void createChests() {
        ChestEntityConfigurer chestEntityInitializer = new ChestEntityConfigurer(engine, assetManager);
        for (int i = 0; i < 30; i++) {
            chestEntityInitializer.addChest(dungeon);
        }
    }
}
