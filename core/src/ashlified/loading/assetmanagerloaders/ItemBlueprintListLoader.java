package ashlified.loading.assetmanagerloaders;

import ashlified.entitycomponentsystem.entityinitializers.ItemEntityConfigurer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class ItemBlueprintListLoader extends AsynchronousAssetLoader<ItemBlueprintListLoader.ItemBlueprintList, ItemBlueprintListLoader.ItemBlueprintListLoaderParameter> {
  private ItemBlueprintListLoader.ItemBlueprintList blueprints;

  public ItemBlueprintListLoader(FileHandleResolver resolver) {
    super(resolver);
    blueprints = new ItemBlueprintListLoader.ItemBlueprintList();
  }

  @Override
  public void loadAsync(AssetManager manager, String fileName, FileHandle file, ItemBlueprintListLoader.ItemBlueprintListLoaderParameter parameter) {
    blueprints = null;
    blueprints = new ItemBlueprintListLoader.ItemBlueprintList();
    FileHandle[] files = getBlueprintFiles(file);
    if (files != null) {
      Json json = new Json();
      for (FileHandle blueprintFileHandle : files) {
        ItemEntityConfigurer.ItemBlueprint blueprint = json.fromJson(ItemEntityConfigurer.ItemBlueprint.class, blueprintFileHandle);
        blueprints.getItemBlueprints().add(blueprint);
      }
    }
  }

  private FileHandle[] getBlueprintFiles(FileHandle dir) {
    String blueprintFileSuffix = "bp";
    return dir.list(blueprintFileSuffix);
  }

  @Override
  public ItemBlueprintListLoader.ItemBlueprintList loadSync(AssetManager manager, String fileName, FileHandle file, ItemBlueprintListLoader.ItemBlueprintListLoaderParameter parameter) {
    ItemBlueprintListLoader.ItemBlueprintList blueprints = this.blueprints;
    this.blueprints = null;
    return blueprints;
  }

  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ItemBlueprintListLoader.ItemBlueprintListLoaderParameter parameter) {
    return null;
  }

  static class ItemBlueprintListLoaderParameter extends AssetLoaderParameters<ItemBlueprintListLoader.ItemBlueprintList> {}

  public static class ItemBlueprintList {

    private ArrayList<ItemEntityConfigurer.ItemBlueprint> itemBlueprints = new ArrayList<>();

    public ArrayList<ItemEntityConfigurer.ItemBlueprint> getItemBlueprints() {
      return itemBlueprints;
    }
  }
}
