package ashlified.systems.factories;

import ashlified.AssetPaths;
import ashlified.components.AnimationControllerComponent;
import ashlified.components.InventoryComponent;
import ashlified.components.ModelInstanceComponent;
import ashlified.components.PositionComponent;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;


public class ChestFactory {

    private final static int ITEM_CAPACITY = 1;
    private final PooledEngine engine;
    private AssetManager assetManager;

    public ChestFactory(Engine engine, AssetManager assetManager) {
        this.engine = (PooledEngine) engine;
        this.assetManager = assetManager;
    }

    public void createChest(Dungeon dungeon) {
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
    }


    /**
     * Class containing animation ids of Chest model for easier access and change. The ids come
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
