package ashlified.factories;

import ashlified.AssetPaths;
import ashlified.components.*;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.loading.assetmanagerloaders.NPCBlueprintListLoader;
import ashlified.loading.assetmanagerloaders.SCMLDataWithResourcesLoader;
import ashlified.util.RandomNumber;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Player;

import java.util.ArrayList;

/**
 * Created by karkoon on 01.04.17.
 */
public class NPCFactory {

    private final static int SPEED = 1;
    private final PooledEngine engine;
    private ArrayList<EnemyNPCBlueprint> blueprints;
    private AssetManager assetManager;

    public NPCFactory(Engine engine, AssetManager assetManager) {
        this.engine = (PooledEngine) engine;
        blueprints = assetManager.get(AssetPaths.NPC_DIRECTORY, NPCBlueprintListLoader.EnemyNPCBlueprintList.class).getEnemyNPCBlueprints();
        this.assetManager = assetManager;
    }

    public void createEnemyNPC(String enemyNPCname, Dungeon dungeon) {
        for (EnemyNPCBlueprint blueprint : blueprints) {
            if (enemyNPCname.equals(blueprint.name)) {

                StatsComponent stats = engine.createComponent(StatsComponent.class);
                PositionComponent position = engine.createComponent(PositionComponent.class);
                SpeedComponent speed = engine.createComponent(SpeedComponent.class);
                InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
                ArmorComponent armor = engine.createComponent(ArmorComponent.class);
                AttackComponent attack = engine.createComponent(AttackComponent.class);
                ViewDistanceComponent viewDistance = engine.createComponent(ViewDistanceComponent.class);
                NameComponent name = engine.createComponent(NameComponent.class);
                SpawnRateComponent spawnRateComponent = engine.createComponent(SpawnRateComponent.class);
                HealthComponent health = engine.createComponent(HealthComponent.class);

                name.setName(blueprint.name);
                DungeonSection dungeonSection = dungeon.getRandomDungeonSection();
                position.setPosition(new Vector3(dungeonSection.getPoint().x, 0, dungeonSection.getPoint().y));
                position.setOccupiedSection(dungeonSection);
                speed.setSpeed(SPEED);
                stats.setDexterity(blueprint.dexterity);
                stats.setStrength(blueprint.strength);
                stats.setWisdom(blueprint.wisdom);
                armor.setArmor(blueprint.armor);
                viewDistance.setViewDistance(blueprint.viewDistance);
                inventory.setMaxItems(blueprint.maxItems);
                //inventory.addUsedItem();
                spawnRateComponent.setSpawnRate(blueprint.spawnRate);
                spawnRateComponent.setStartLevel(blueprint.startLevel);
                spawnRateComponent.setEndLevel(blueprint.endLevel);
                spawnRateComponent.setLevelTheme(blueprint.levelTheme);
                health.setMaxHealth(blueprint.maxHealth);
                health.setHealth(blueprint.maxHealth);

                Entity entity = engine.createEntity();
                entity.add(stats);
                entity.add(position);
                entity.add(speed);
                entity.add(inventory);
                entity.add(retrieveGraphicalRepresentation(blueprint));
                entity.add(armor);
                entity.add(attack);
                entity.add(viewDistance);
                entity.add(name);
                entity.add(spawnRateComponent);
                entity.add(health);
                engine.addEntity(entity);
            }
        }
    }

    private GraphicalComponent retrieveGraphicalRepresentation(EnemyNPCBlueprint blueprint) {
        GraphicalComponent animationsComponent = engine.createComponent(GraphicalComponent.class);
        Data data = assetManager.get(AssetPaths.SCML_FILE, SCMLDataWithResourcesLoader.SCMLDataWithResources.class).getData();
        Player player = new Player(data.getEntity(blueprint.scmlPrefix));
        int startTime = RandomNumber.nextInt(1000);
        player.setTime(startTime);
        animationsComponent.setPlayer(player);
        return animationsComponent;
    }

    public static class EnemyNPCBlueprint {

        private String name;
        private int strength;
        private int wisdom;
        private int dexterity;
        private int maxHealth;
        private int armor;
        private int startLevel;
        private int endLevel;
        private int spawnRate;
        private String levelTheme;
        private int maxItems;
        private int viewDistance;
        private String scmlPrefix;
    }
}
