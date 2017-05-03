package ashlified.factories;

import ashlified.components.*;
import ashlified.spriterutils.DecalLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.MappedByteBuffer;
import java.util.*;

/**
 * Created by karkoon on 01.04.17.
 */
public class NPCFactory {

    private final static int SPEED = 1;
    private final static Vector3 DEFAULT_POSITION = new Vector3(0, 0, 0);
    private final PooledEngine engine;
    private ArrayList<EnemyNPCBlueprint> blueprints;

    public NPCFactory(PooledEngine engine) {
        this.engine = engine;
        String dirPath = "./npcAnimations";
        blueprints = new EnemyNPCBlueprintLoader().loadBlueprintsFromDirectory(dirPath);
    }

    public Entity createEnemyNPC(String enemyNPCname) {
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
                position.setPosition(DEFAULT_POSITION.cpy());
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
            }
        }
        return entity;
    }


    private AnimationsComponent retrieveAnimation(EnemyNPCBlueprint blueprint) {
        FileHandle handle = Gdx.files.internal(blueprint.scmlPath);
        Data data = new SCMLReader(handle.read()).getData();
        DecalLoader loader = new DecalLoader(data);
        loader.load(handle.file());
        AnimationsComponent animationsComponent = engine.createComponent(AnimationsComponent.class);
        animationsComponent.setPlayer(new Player(data.getEntity(0)));
        animationsComponent.setLoader(loader);
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
            File[] files = getAnimationFiles(dirPath);
            if (files != null) {
                Json json = new Json();
                for (File blueprintFile : files) {
                    FileHandle handle = Gdx.files.internal(blueprintFile.getPath());
                    EnemyNPCBlueprint blueprint = json.fromJson(EnemyNPCBlueprint.class, handle);
                    blueprints.add(blueprint);
                }
            }
            return blueprints;
        }

        private File[] getAnimationFiles(String dirPath) {
            File dir = new File(dirPath);
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(".*\\.scml");
                }
            });
            return files;
        }
    }
}
