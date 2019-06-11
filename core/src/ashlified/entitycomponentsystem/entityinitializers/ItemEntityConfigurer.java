package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.AssetPaths;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.ArmorComponent;
import ashlified.entitycomponentsystem.components.DescriptionComponent;
import ashlified.entitycomponentsystem.components.DroppedItemComponent;
import ashlified.entitycomponentsystem.components.FloatingSpeedComponent;
import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.ItemIconComponent;
import ashlified.entitycomponentsystem.components.ItemTypeComponent;
import ashlified.entitycomponentsystem.components.ModelInstanceComponent;
import ashlified.entitycomponentsystem.components.NameComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import ashlified.entitycomponentsystem.components.SpawnRateComponent;
import ashlified.entitycomponentsystem.components.StatsComponent;
import ashlified.loading.assetmanagerloaders.Blueprint;
import ashlified.loading.assetmanagerloaders.BlueprintListLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

  void configureNewItem(Dungeon dungeon) {
    for (ItemBlueprint blueprint : blueprints) {
      StatsComponent stats = engine.createComponent(StatsComponent.class);
      PositionComponent position = engine.createComponent(PositionComponent.class);
      ArmorComponent armor = engine.createComponent(ArmorComponent.class);
      NameComponent name = engine.createComponent(NameComponent.class);
      DescriptionComponent description = engine.createComponent(DescriptionComponent.class);
      SpawnRateComponent spawnRateComponent = engine.createComponent(SpawnRateComponent.class);
      HealthComponent health = engine.createComponent(HealthComponent.class);
      FloatingSpeedComponent floatingSpeed = engine.createComponent(FloatingSpeedComponent.class);
      ItemTypeComponent type = engine.createComponent(ItemTypeComponent.class);
      ItemIconComponent icon = engine.createComponent(ItemIconComponent.class);

      DroppedItemComponent dropped = engine.createComponent(DroppedItemComponent.class);

      name.setName(blueprint.name);
      description.setDescription(blueprint.description);
      DungeonSection dungeonSection = dungeon.getRandomDungeonSection();
      position.setPosition(dungeonSection.getPosition().x, 4.0f, dungeonSection.getPosition().z);
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
      floatingSpeed.setSpeed(0.2f);
      type.setType(Enum.valueOf(ItemTypeComponent.Type.class, blueprint.type));
      icon.setIcon(assetManager.get(AssetPaths.ITEM_ATLAS, TextureAtlas.class).findRegion(blueprint.name));

      Entity entity = engine.createEntity();
      entity.add(stats)
        .add(position)
        .add(retrieveGraphicalRepresentation(blueprint))
        .add(armor)
        .add(name)
        .add(description)
        .add(spawnRateComponent)
        .add(health)
        .add(dropped)
        .add(floatingSpeed)
        .add(type)
        .add(icon);
      engine.addEntity(entity);

      dungeonSection.addOccupyingObject(entity);

    }
  }

  private ModelInstanceComponent retrieveGraphicalRepresentation(ItemBlueprint itemBlueprint) {
    ModelInstanceComponent modelInstanceComponent = engine.createComponent(ModelInstanceComponent.class);
    ModelInstance modelInst = new ModelInstance(assetManager.get(AssetPaths.PLANE_MODEL, Model.class));
    Material material = modelInst.materials.first();
    material.set(
      ColorAttribute.createDiffuse(0.8f, 0.8f, 0.8f, 1f),
      new FloatAttribute(FloatAttribute.AlphaTest, 0.5f),
      new BlendingAttribute(),
      TextureAttribute.createDiffuse(assetManager.get(AssetPaths.ITEM_ATLAS, TextureAtlas.class).findRegion(itemBlueprint.name)));
    modelInstanceComponent.setModelInstance(modelInst);
    return modelInstanceComponent;
  }

  public static class ItemBlueprint implements Blueprint {
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
    private String type;
  }
}
