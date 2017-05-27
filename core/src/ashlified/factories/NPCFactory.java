package ashlified.factories;

import ashlified.components.*;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.spriterutils.PlaneDrawer;
import ashlified.spriterutils.PlaneLoader;
import ashlified.util.RandomNumber;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.brashmonkey.spriter.*;

import java.util.ArrayList;

/**
 * Created by karkoon on 01.04.17.
 */
public class NPCFactory {

    private final static int SPEED = 1;
    private final PooledEngine engine;
    private ArrayList<EnemyNPCBlueprint> blueprints;

    public NPCFactory(Engine engine) {
        this.engine = (PooledEngine) engine;
        String dirPath = "./npc";
        blueprints = new EnemyNPCBlueprintLoader().loadBlueprintsFromDirectory(dirPath);
    }

    public Entity createEnemyNPC(String enemyNPCname, Dungeon dungeon) throws Exception {
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

        Entity entity = null;
        for (EnemyNPCBlueprint blueprint : blueprints) {
            if (enemyNPCname.equals(blueprint.name)) {
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

                entity = engine.createEntity();
                entity.add(stats);
                entity.add(position);
                entity.add(speed);
                entity.add(inventory);
                entity.add(retrieveAnimation(blueprint));
                entity.add(armor);
                entity.add(attack);
                entity.add(viewDistance);
                entity.add(name);
                entity.add(spawnRateComponent);
                entity.add(health);
                engine.addEntity(entity);
            }
        }
        return entity;
    }

    private AnimationsComponent retrieveAnimation(EnemyNPCBlueprint blueprint) {
        FileHandle handle = Gdx.files.internal("npc/" + blueprint.scmlPath);
        Data data = new SCMLReader(handle.read()).getData();
        Loader loader = new PlaneLoader(data);
        loader.load(handle.file());
        Drawer planeDrawer = new PlaneDrawer(loader);
        AnimationsComponent animationsComponent = engine.createComponent(AnimationsComponent.class);
        Player player = new Player(data.getEntity(0));
        player.setTime(RandomNumber.nextInt(5000));
        animationsComponent.setPlayer(player);
        animationsComponent.setLoader(loader);
        animationsComponent.setDrawer(planeDrawer);
        return animationsComponent;
    }

    private static class EnemyNPCBlueprint {

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
        private String scmlPath;
    }

    private static class EnemyNPCBlueprintLoader {

        ArrayList<EnemyNPCBlueprint> loadBlueprintsFromDirectory(String dirPath) {
            ArrayList<EnemyNPCBlueprint> blueprints = new ArrayList<>();
            FileHandle[] files = getBlueprintFiles(dirPath);
            if (files != null) {
                Json json = new Json();
                for (FileHandle blueprintFileHandle : files) {
                    EnemyNPCBlueprint blueprint = json.fromJson(EnemyNPCBlueprint.class, blueprintFileHandle);
                    blueprints.add(blueprint);
                }
            }
            return blueprints;
        }

        private FileHandle[] getBlueprintFiles(String dirPath) {
            FileHandle dir = Gdx.files.internal(dirPath);
            FileHandle[] files = dir.list("bp");
            return files;
        }
    }
}
