package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.AssetPaths;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.*;
import ashlified.loading.assetmanagerloaders.NpcBlueprintListLoader;
import ashlified.loading.assetmanagerloaders.ScmlDataWithResourcesLoader;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Player;

import java.util.ArrayList;

/**
 * Created by karkoon on 01.04.17.
 * Obtains an Entity and Components and configures them into an EnemyNPC.
 */
public class EnemyNPCEntityConfigurer {

    private final PooledEngine engine;
    private ArrayList<EnemyNPCBlueprint> blueprints;
    private AssetManager assetManager;

    EnemyNPCEntityConfigurer(Engine engine, AssetManager assetManager) {
        this.engine = (PooledEngine) engine;
        blueprints = assetManager.get(AssetPaths.NPC_DIRECTORY, NpcBlueprintListLoader.EnemyNPCBlueprintList.class).getEnemyNPCBlueprints();
        this.assetManager = assetManager;
    }

    void configureNewEnemyNPC(String enemyNPCname, Dungeon dungeon) {
        for (EnemyNPCBlueprint blueprint : blueprints) {
            if (enemyNPCname.equals(blueprint.name)) {

                StatsComponent stats = engine.createComponent(StatsComponent.class);
                PositionComponent position = engine.createComponent(PositionComponent.class);
                MovingDirectionComponent direction = engine.createComponent(MovingDirectionComponent.class);
                InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
                ArmorComponent armor = engine.createComponent(ArmorComponent.class);
                ViewDistanceComponent viewDistance = engine.createComponent(ViewDistanceComponent.class);
                NameComponent name = engine.createComponent(NameComponent.class);
                SpawnRateComponent spawnRateComponent = engine.createComponent(SpawnRateComponent.class);
                HealthComponent health = engine.createComponent(HealthComponent.class);
                TargetComponent target = engine.createComponent(TargetComponent.class);

                name.setName(blueprint.name);
                DungeonSection dungeonSection = dungeon.getRandomDungeonSection();
                position.setPosition(dungeonSection.getPosition());
                position.setOccupiedSection(dungeonSection);
                direction.setDirection(CardinalDirection.NORTH);
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
                entity.add(stats)
                        .add(position)
                        .add(direction)
                        .add(inventory)
                        .add(retrieveGraphicalRepresentation(blueprint))
                        .add(armor).add(viewDistance)
                        .add(name)
                        .add(spawnRateComponent)
                        .add(health)
                        .add(target);
                engine.addEntity(entity);

                dungeonSection.addOccupyingObject(entity);
                return;
            }
        }
    }

    private SpriterModelComponent retrieveGraphicalRepresentation(EnemyNPCBlueprint blueprint) {
        SpriterModelComponent animationsComponent = engine.createComponent(SpriterModelComponent.class);
        Data data = assetManager.get(AssetPaths.SCML_FILE, ScmlDataWithResourcesLoader.SCMLDataWithResources.class).getData();
        Player player = new Player(data.getEntity(blueprint.scmlPrefix));
        int startTime = MathUtils.random(1000); // stops enemies from having synchronized animations
        player.setTime(startTime);
        animationsComponent.setSpriterAnimationController(new SpriterModelComponent.SpriterAnimationController());
        animationsComponent.getSpriterAnimationController().setPlayer(player);
        return animationsComponent;
    }

    /**
     * Contains all of the names of available entities.
     */
    public enum EnemyName {
        GHOST("Ghost"), HELL_KNIGHT("Hell knight"), SNORG("Snorg");

        private String value;

        EnemyName(String name) {
            this.value = name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Used in deserialization.
     */
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
