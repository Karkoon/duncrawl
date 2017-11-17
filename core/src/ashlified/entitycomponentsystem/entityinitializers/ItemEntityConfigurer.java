package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.AssetPaths;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.*;
import ashlified.loading.assetmanagerloaders.BlueprintListLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

import java.util.ArrayList;

public class ItemEntityConfigurer {

    private PooledEngine engine;
    private AssetManager assetManager;
    private ArrayList<ItemBlueprint> blueprints;

    ItemEntityConfigurer(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.blueprints = assetManager.get(AssetPaths.ITEM_DIRECTORY, BlueprintListLoader.BlueprintList.class).getBlueprints();
        this.assetManager = assetManager;
    }

    void configureNewItem(String itemName, Dungeon dungeon) {
        for (ItemBlueprint blueprint : blueprints) {
            if (itemName.equals(blueprint.name)) {
                StatsComponent stats = engine.createComponent(StatsComponent.class);
                PositionComponent position = engine.createComponent(PositionComponent.class);
                ArmorComponent armor = engine.createComponent(ArmorComponent.class);
                NameComponent name = engine.createComponent(NameComponent.class);
                DescriptionComponent description = engine.createComponent(DescriptionComponent.class);
                SpawnRateComponent spawnRateComponent = engine.createComponent(SpawnRateComponent.class);
                HealthComponent health = engine.createComponent(HealthComponent.class);

                name.setName(blueprint.name);
                description.setDescription(blueprint.description);
                DungeonSection dungeonSection = dungeon.getRandomDungeonSection();
                position.setPosition(dungeonSection.getPosition());
                position.setOccupiedSection(dungeonSection);
                stats.setDexterity(blueprint.dexterity);
                stats.setStrength(blueprint.strength);
                stats.setWisdom(blueprint.wisdom);
                armor.setArmor(blueprint.armor);
                spawnRateComponent.setSpawnRate(blueprint.spawnRate);
                spawnRateComponent.setStartLevel(blueprint.startLevel);
                spawnRateComponent.setEndLevel(blueprint.endLevel);
                spawnRateComponent.setLevelTheme(blueprint.levelTheme);
                health.setMaxHealth(blueprint.maxHealth);
                health.setHealth(blueprint.maxHealth);

                Entity entity = engine.createEntity();
                entity.add(stats)
                        .add(position)
                        .add(retrieveGraphicalRepresentation(blueprint))
                        .add(armor)
                        .add(name)
                        .add(description)
                        .add(spawnRateComponent)
                        .add(health);
                engine.addEntity(entity);

                dungeonSection.addOccupyingObject(entity);
            }
        }
    }

    private ModelInstanceComponent retrieveGraphicalRepresentation(ItemBlueprint itemBlueprint) {
        ModelInstanceComponent modelInstanceComponent = engine.createComponent(ModelInstanceComponent.class);
        ModelInstance modelInst = new ModelInstance(assetManager.get(AssetPaths.PLANE_MODEL, Model.class));
        Material material = modelInst.materials.first();
        material.set(ColorAttribute.createDiffuse(0.8f, 0.8f, 0.8f, 1f),
                new FloatAttribute(FloatAttribute.AlphaTest, 0.5f),
                new BlendingAttribute(),
                TextureAttribute.createDiffuse(assetManager.get("items/Ring of Strength.png", Texture.class)));
        modelInstanceComponent.setInstance(modelInst);
        Gdx.app.log("ddsds", "adqweqwed");
        return modelInstanceComponent;
    }

    public enum ItemName {
        RING("Ring of Strength");

        private String name;

        ItemName(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }
    }

    public static class ItemBlueprint {
        private String name;
        private String description;
        private int strength;
        private int wisdom;
        private int dexterity;
        private int maxHealth;
        private int armor;
        private int startLevel;
        private int endLevel;
        private int spawnRate;
        private String levelTheme;
    }
}
