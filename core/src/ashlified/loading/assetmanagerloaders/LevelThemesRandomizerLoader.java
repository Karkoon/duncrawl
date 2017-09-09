package ashlified.loading.assetmanagerloaders;

import ashlified.graphics.LevelTheme;
import ashlified.graphics.LevelThemesRandomizer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

/**
 * Loads theme files from json and creates a randomizer class.
 */
public class LevelThemesRandomizerLoader extends AsynchronousAssetLoader<LevelThemesRandomizer, LevelThemesRandomizerLoader.LevelThemesLoaderParameters> {

    private LevelThemesRandomizer levelThemesRandomizer;
    private Json json;

    public LevelThemesRandomizerLoader(FileHandleResolver resolver) {
        super(resolver);
        json = new Json();
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, LevelThemesLoaderParameters parameter) {
        levelThemesRandomizer = null;
        levelThemesRandomizer = new LevelThemesRandomizer(loadLevelThemes(file));
    }

    @Override
    public LevelThemesRandomizer loadSync(AssetManager manager, String fileName, FileHandle file, LevelThemesLoaderParameters parameter) {
        LevelThemesRandomizer levelThemesRandomizer = this.levelThemesRandomizer;
        this.levelThemesRandomizer = null;
        return levelThemesRandomizer;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, LevelThemesLoaderParameters parameter) {
        return null;
    }

    private ArrayList<LevelTheme> loadLevelThemes(FileHandle file) {
        FileHandle[] themeFiles = file.list();
        ArrayList<LevelTheme> levelThemes = new ArrayList<>();
        if (themeFiles != null) {
            for (FileHandle themeFile : themeFiles) {
                levelThemes.add(json.fromJson(LevelTheme.class, themeFile));
            }
        }
        return levelThemes;
    }

    public static class LevelThemesLoaderParameters extends AssetLoaderParameters<LevelThemesRandomizer> {

    }
}
