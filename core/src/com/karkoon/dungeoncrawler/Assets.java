package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.karkoon.dungeoncrawler.WallModelsAccessor.Theme;

import java.io.File;
import java.util.Random;

/**
 * Created by Pc on 2016-09-10.
 * Used with asset manager to give easy access to files.
 * Contains game-generated models.
 */
public class Assets {

    private static ObjectMap<Theme, WallModelsAccessor> themedModels = new ObjectMap<>();

    public static WallModelsAccessor getWallModelsAccessor() {
        if (themedModels.size > 0) {
            ObjectMap.Keys<Theme> themes = themedModels.keys();
            int sum = 0;
            for (Theme theme : themes) {
                sum += theme.chanceOfAppearing;
            }
            themes = themedModels.keys();
            int roll = new Random().nextInt(sum);
            int partial = 0;
            for (Theme theme : themes) {
                partial += theme.chanceOfAppearing;
                if (roll <= partial) {
                    return themedModels.get(theme);
                }
            }
        } else {
            populateThemedModels();
        }
        return getWallModelsAccessor();
    }

    private static void populateThemedModels() {
        Json json = new Json();
        File themeDir = new File("themes/");
        for (File themeFile : themeDir.listFiles()) {
            Theme theme = json.fromJson(Theme.class, new FileHandle(themeFile));
            themedModels.put(theme, new WallModelsAccessor(theme, DungeonSection.getSize(), DungeonSection.getHeight()));
        }
    }
}
