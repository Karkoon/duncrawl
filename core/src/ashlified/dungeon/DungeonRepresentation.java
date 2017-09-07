package ashlified.dungeon;

import ashlified.AssetPaths;
import ashlified.graphics.WallModelsAccessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;

public class DungeonRepresentation {

    private ArrayList<ModelInstance> dungeonSectionRepresentations = new ArrayList<>();

    public DungeonRepresentation(Dungeon dungeon, AssetManager manager) {

        LevelThemesRandomizer themesRandomizer = manager.get(AssetPaths.LEVEL_THEMES_DIR, LevelThemesRandomizer.class);
        LevelTheme selectedLevelTheme = themesRandomizer.randomlySelectLevelTheme();

        WallModelsAccessor accessor = new WallModelsAccessor(selectedLevelTheme, manager);
        for (DungeonSection section : dungeon.getGrid()) {
            dungeonSectionRepresentations.add(new DungeonSectionRepresentation(section, accessor).getModelInstance());
        }
    }

    public ArrayList<ModelInstance> getModelInstances() {
        return dungeonSectionRepresentations;
    }
}