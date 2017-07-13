package ashlified.dungeon;

import ashlified.AssetPaths;
import ashlified.graphics.WallModelsAccessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

public class DungeonRepresentation implements RenderableProvider {

    private List<DungeonSectionRepresentation> models = new ArrayList<>();

    public DungeonRepresentation(Dungeon dungeon, AssetManager manager) {
        LevelThemesRandomizer themesRandomizer = manager.get(AssetPaths.LEVEL_THEMES_DIR, LevelThemesRandomizer.class);
        LevelTheme selectedLevelTheme = themesRandomizer.randomlySelectLevelTheme();
        WallModelsAccessor accessor = new WallModelsAccessor(selectedLevelTheme, manager);
        for (DungeonSection section : dungeon.getGrid()) {
            models.add(new DungeonSectionRepresentation(section, accessor));
        }
    }

    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        for (DungeonSectionRepresentation representation : models) {
            representation.getRenderables(renderables, pool);
        }
    }
}