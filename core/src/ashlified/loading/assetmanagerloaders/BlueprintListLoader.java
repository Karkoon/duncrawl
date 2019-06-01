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

public class BlueprintListLoader<T extends Blueprint> extends AsynchronousAssetLoader<BlueprintListLoader.BlueprintList<T>, BlueprintListLoader.BlueprintListLoaderParameter<T>> {

  private BlueprintList<T> blueprints;

  public BlueprintListLoader(FileHandleResolver resolver) {
    super(resolver);
    blueprints = new BlueprintList<>();
  }

  @Override
  public void loadAsync(AssetManager manager, String fileName, FileHandle file, BlueprintListLoaderParameter<T> parameter) {
    blueprints = null;
    blueprints = new BlueprintList<>();
    FileHandle[] files = getBlueprintFiles(file);
    if (files != null) {
      Json json = new Json();
      for (FileHandle blueprintFileHandle : files) {
        T blueprint = json.fromJson(parameter.tClass, blueprintFileHandle);
        blueprints.getBlueprints().add(blueprint);
      }
    }
  }

  private FileHandle[] getBlueprintFiles(FileHandle dir) {
    String blueprintFileSuffix = "bp";
    return dir.list(blueprintFileSuffix);
  }

  @Override
  public BlueprintList<T> loadSync(AssetManager manager, String fileName, FileHandle file, BlueprintListLoaderParameter<T> parameter) {
    BlueprintList<T> blueprints = this.blueprints;
    this.blueprints = null;
    return blueprints;
  }

  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BlueprintListLoaderParameter<T> parameter) {
    return null;
  }

  public static class BlueprintListLoaderParameter<T extends Blueprint> extends AssetLoaderParameters<BlueprintList<T>> {
    Class<T> tClass;

    public BlueprintListLoaderParameter(Class<T> tClass) {
      this.tClass = tClass;
    }
  }

  public static class BlueprintList<T extends Blueprint> {

    private ArrayList<T> blueprints = new ArrayList<>();

    public ArrayList<T> getBlueprints() {
      return blueprints;
    }
  }
}