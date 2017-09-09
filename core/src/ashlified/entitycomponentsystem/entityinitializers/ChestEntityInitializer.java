package ashlified.entitycomponentsystem.entityinitializers;

import ashlified.AssetPaths;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.AnimationControllerComponent;
import ashlified.entitycomponentsystem.components.InventoryComponent;
import ashlified.entitycomponentsystem.components.ModelInstanceComponent;
import ashlified.entitycomponentsystem.components.PositionComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

/**
 * Creates and configures a chest entity.
 */
public class ChestEntityInitializer {

    private final static int ITEM_CAPACITY = 1;
    private final PooledEngine engine;
    private AssetManager assetManager;

    public ChestEntityInitializer(Engine engine, AssetManager assetManager) {
        this.engine = (PooledEngine) engine;
        this.assetManager = assetManager;
    }

    public void addChest(Dungeon dungeon) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
        ModelInstanceComponent representation = engine.createComponent(ModelInstanceComponent.class);
        AnimationControllerComponent animation = engine.createComponent(AnimationControllerComponent.class);

        DungeonSection dungeonSection = dungeon.getRandomDungeonSection();
        position.setPosition(dungeonSection.getPosition());
        position.setOccupiedSection(dungeonSection);

        inventory.setMaxItems(ITEM_CAPACITY);
        //inventory.addUsedItem();

        representation.setInstance(new ModelInstance(assetManager.get(AssetPaths.CHEST_MODEL, Model.class)));

        animation.setController(new AnimationController(representation.getInstance()));
        animation.getController().setAnimation(AnimationID.OPEN.value, -1);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(inventory);
        entity.add(representation);
        entity.add(animation);
        engine.addEntity(entity);

        dungeonSection.addOccupyingObject(entity);
    }


    /**
     * Class containing animation ids of the chest for easier access and change. The ids come
     * from Blender animations.
     */
    enum AnimationID {

        OPEN("Armature|Open"), CLOSE("Armature|Close");

        String value;

        AnimationID(String value) {
            this.value = value;
        }
    }
}
