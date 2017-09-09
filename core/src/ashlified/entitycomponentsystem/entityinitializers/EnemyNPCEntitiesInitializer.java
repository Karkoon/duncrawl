package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.AssetPaths;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.DirectionComponent;
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
 * Creates and configures an EnemyNPC entity.
 */
public class EnemyNPCEntitiesInitializer {

    private final PooledEngine engine;
    private ArrayList<EnemyNPCBlueprint> blueprints;
    private AssetManager assetManager;

    public EnemyNPCEntitiesInitializer(Engine engine, AssetManager assetManager) {
        this.engine = (PooledEngine) engine;
        blueprints = assetManager.get(AssetPaths.NPC_DIRECTORY, NpcBlueprintListLoader.EnemyNPCBlueprintList.class).getEnemyNPCBlueprints();
        this.assetManager = assetManager;
    }

    public void addEnemyNPC(String enemyNPCname, Dungeon dungeon) {
        for (EnemyNPCBlueprint blueprint : blueprints) {
            if (enemyNPCname.equals(blueprint.name)) {

                ashlified.entitycomponentsystem.components.StatsComponent stats = engine.createComponent(ashlified.entitycomponentsystem.components.StatsComponent.class);
                ashlified.entitycomponentsystem.components.PositionComponent position = engine.createComponent(ashlified.entitycomponentsystem.components.PositionComponent.class);
                ashlified.entitycomponentsystem.components.DirectionComponent direction = engine.createComponent(DirectionComponent.class);
                ashlified.entitycomponentsystem.components.InventoryComponent inventory = engine.createComponent(ashlified.entitycomponentsystem.components.InventoryComponent.class);
                ashlified.entitycomponentsystem.components.ArmorComponent armor = engine.createComponent(ashlified.entitycomponentsystem.components.ArmorComponent.class);
                ashlified.entitycomponentsystem.components.AttackComponent attack = engine.createComponent(ashlified.entitycomponentsystem.components.AttackComponent.class);
                ashlified.entitycomponentsystem.components.ViewDistanceComponent viewDistance = engine.createComponent(ashlified.entitycomponentsystem.components.ViewDistanceComponent.class);
                ashlified.entitycomponentsystem.components.NameComponent name = engine.createComponent(ashlified.entitycomponentsystem.components.NameComponent.class);
                ashlified.entitycomponentsystem.components.SpawnRateComponent spawnRateComponent = engine.createComponent(ashlified.entitycomponentsystem.components.SpawnRateComponent.class);
                ashlified.entitycomponentsystem.components.HealthComponent health = engine.createComponent(ashlified.entitycomponentsystem.components.HealthComponent.class);
                ashlified.entitycomponentsystem.components.TargetComponent target = engine.createComponent(ashlified.entitycomponentsystem.components.TargetComponent.class);

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
                entity.add(stats);
                entity.add(position);
                entity.add(direction);
                entity.add(inventory);
                entity.add(retrieveGraphicalRepresentation(blueprint));
                entity.add(armor);
                entity.add(attack);
                entity.add(viewDistance);
                entity.add(name);
                entity.add(spawnRateComponent);
                entity.add(health);
                entity.add(target);
                engine.addEntity(entity);

                dungeonSection.addOccupyingObject(entity);
            }
        }
    }

    private ashlified.entitycomponentsystem.components.SpriterModelComponent retrieveGraphicalRepresentation(EnemyNPCBlueprint blueprint) {
        ashlified.entitycomponentsystem.components.SpriterModelComponent animationsComponent = engine.createComponent(ashlified.entitycomponentsystem.components.SpriterModelComponent.class);
        Data data = assetManager.get(AssetPaths.SCML_FILE, ScmlDataWithResourcesLoader.SCMLDataWithResources.class).getData();
        Player player = new Player(data.getEntity(blueprint.scmlPrefix));
        int startTime = MathUtils.random(1000); // stops enemies from having synchronized animations
        player.setTime(startTime);
        animationsComponent.setPlayer(player);
        return animationsComponent;
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
