package ashlified;

import ashlified.dungeon.LevelTheme;
import ashlified.dungeon.WallModelsAccessor;
import ashlified.util.RandomNumber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Pc on 2016-09-10.
 * Used with asset manager to give easy access to files.
 * Contains game-generated models.
 */
class Assets {

    private static ObjectMap<LevelTheme, WallModelsAccessor> themedModels = populateThemedModels();

    private Assets() { }

    private static ObjectMap<LevelTheme, WallModelsAccessor> populateThemedModels() {

        ObjectMap<LevelTheme, WallModelsAccessor> themedModels = new ObjectMap<>();
        Json json = new Json();
        FileHandle themeDir = Gdx.files.internal("themes/");
        FileHandle[] themeFiles = themeDir.list();
        if (themeFiles != null) {
            for (FileHandle themeFile : themeFiles) {
                LevelTheme theme = json.fromJson(LevelTheme.class, themeFile);
                themedModels.put(theme, new WallModelsAccessor(theme));
            }
        }
       return themedModels;
    }

    public static WallModelsAccessor getWallModelsAccessor() {
        ObjectMap.Keys<LevelTheme> themes = themedModels.keys();
        int sum = 0;
        for (LevelTheme theme : themes) {
            sum += theme.getSpawnRate();
        }
        themes = themedModels.keys();
        int roll = RandomNumber.nextInt(sum);
        int partial = 0;
        for (LevelTheme theme : themes) {
            partial += theme.getSpawnRate();
            if (roll <= partial) {
                return themedModels.get(theme);
            }
        }
        return null;
    }
}
