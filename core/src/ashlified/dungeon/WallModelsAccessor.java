package ashlified.dungeon;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.EnumMap;

/**
 * Created by kacper on 25.12.16.
 * Gives access to models.
 */
public class WallModelsAccessor {

    private final EnumMap<WallType, Model> models;

    public WallModelsAccessor(LevelTheme theme) {
        WallModelsProvider retriever = new RuntimeCreatedWallModelsProvider();
        this.models = retriever.getNewModels(theme);
    }

    public Model get(WallType key) {
        return models.get(key);
    }

}