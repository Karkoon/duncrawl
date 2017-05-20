package ashlified;

import ashlified.dungeon.WallModelsAccessor;
import ashlified.dungeon.WallModelsAccessor.Theme;
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
public class Assets {

    private static ObjectMap<Theme, WallModelsAccessor> themedModels = populateThemedModels();

    private Assets() { }

    private static ObjectMap<Theme, WallModelsAccessor> populateThemedModels() {
        ObjectMap<Theme, WallModelsAccessor> themedModels = new ObjectMap<>();
        Json json = new Json();
        FileHandle themeDir = Gdx.files.internal("themes/");
        FileHandle[] themeFiles = themeDir.list();
        if (themeFiles != null) {
            for (FileHandle themeFile : themeFiles) {
                Theme theme = json.fromJson(Theme.class, themeFile);
                themedModels.put(theme,
                        new WallModelsAccessor(theme));
            }
        }
       return themedModels;
    }

    public static WallModelsAccessor getWallModelsAccessor() {
        ObjectMap.Keys<Theme> themes = themedModels.keys();
        int sum = 0;
        for (Theme theme : themes) {
            sum += theme.getSpawnRate();
        }
        themes = themedModels.keys();
        int roll = RandomNumber.nextInt(sum);
        int partial = 0;
        Gdx.app.log("dadawdada", themes.toString());
        for (Theme theme : themes) {
            partial += theme.getSpawnRate();
            if (roll <= partial) {
                return themedModels.get(theme);
            }
        }
        return null;
    }
}
