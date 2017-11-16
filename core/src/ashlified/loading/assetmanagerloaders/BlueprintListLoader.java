package ashlified.loading.assetmanagerloaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class BlueprintListLoader<T> extends AsynchronousAssetLoader<BlueprintListLoader<T>.BlueprintList<T>, BlueprintListLoader<T>.BlueprintListLoaderParameter> {

    private BlueprintList blueprints;
    private Class<T> tClass;

    public BlueprintListLoader(FileHandleResolver resolver, Class<T> tClass) {
        super(resolver);
        this.tClass = tClass;
        blueprints = new BlueprintList();
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, BlueprintListLoaderParameter parameter) {
        blueprints = null;
        blueprints = new BlueprintList();
        FileHandle[] files = getBlueprintFiles(file);
        if (files != null) {
            Json json = new Json();
            for (FileHandle blueprintFileHandle : files) {
                T blueprint = json.fromJson(tClass, blueprintFileHandle);
                blueprints.getBlueprints().add(blueprint);
            }
        }
    }

    private FileHandle[] getBlueprintFiles(FileHandle dir) {
        String blueprintFileSuffix = "bp";
        return dir.list(blueprintFileSuffix);
    }

    @Override
    public BlueprintList loadSync(AssetManager manager, String fileName, FileHandle file, BlueprintListLoaderParameter parameter) {
        BlueprintList blueprints = this.blueprints;
        this.blueprints = null;
        return blueprints;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BlueprintListLoaderParameter parameter) {
        return null;
    }

    static class BlueprintListLoaderParameter extends AssetLoaderParameters<BlueprintList> {
    }

    public static class BlueprintList<T> {

        private ArrayList<T> blueprints = new ArrayList<>();

        public ArrayList<T> getBlueprints() {
            return blueprints;
        }
    }
}