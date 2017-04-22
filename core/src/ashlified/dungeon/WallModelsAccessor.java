package ashlified.dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.EnumMap;

/**
 * Created by kacper on 25.12.16.
 * Gives access to models.
 */
public class WallModelsAccessor {

    private final EnumMap<WallType, Model> models;

    public WallModelsAccessor(Theme theme, float size, float height) {
        WallModelsProvider retriever = new RuntimeCreatedWallModelsProvider();
        this.models = retriever.getNewModels(theme, size, height);
    }

    public Model get(WallType key) {
        return models.get(key);
    }

    public enum WallType {

        TWO_SIDES, CORNER, ONE_SIDE, NO_SIDES

    }

    /**
     * Class for deserialization.
     */

    public static class Theme {

        public final static int NEVER = 0;
        public final static int START = 0;

        String name;
        Color color;
        int chanceOfAppearing;
        int startLevel;
        int endLevel;
    }
}