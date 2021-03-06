package ashlified.loading.assetmanagerloaders;

import ashlified.AssetPaths;
import ashlified.graphics.spriterutils.PoolPlaneAtlasLoader;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.SCMLReader;

import static ashlified.loading.assetmanagerloaders.ScmlDataWithResourcesLoader.DataAndResourcesParameter;
import static ashlified.loading.assetmanagerloaders.ScmlDataWithResourcesLoader.SCMLDataWithResources;

/**
 * Loads SCML Spriter files and associated image loader (a bit of a loader-ception, it's a library limitation).
 */
public class ScmlDataWithResourcesLoader extends AsynchronousAssetLoader<SCMLDataWithResources, DataAndResourcesParameter> {

  private SCMLDataWithResources dataAndResources;

  public ScmlDataWithResourcesLoader(FileHandleResolver resolver) {
    super(resolver);
  }

  @Override
  public void loadAsync(AssetManager manager, String fileName, FileHandle file, DataAndResourcesParameter parameter) {
    dataAndResources = new SCMLDataWithResources();
    dataAndResources.setData(new SCMLReader(file.read()).getData());
    dataAndResources.setLoader(new PoolPlaneAtlasLoader(dataAndResources.getData(), manager));
    dataAndResources.getLoader().load(file.file());
  }

  @Override
  public SCMLDataWithResources loadSync(AssetManager manager, String fileName, FileHandle file, DataAndResourcesParameter parameter) {
    SCMLDataWithResources dataAndResources = this.dataAndResources;
    this.dataAndResources = null;
    return dataAndResources;
  }

  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, DataAndResourcesParameter parameter) {
    Array<AssetDescriptor> assetDescriptors = new Array<>();
    assetDescriptors.add(new AssetDescriptor<>(AssetPaths.PLANE_MODEL, Model.class));
    assetDescriptors.add(new AssetDescriptor<>(AssetPaths.NPC_ATLAS, TextureAtlas.class));
    return assetDescriptors;
  }

  public static class DataAndResourcesParameter extends AssetLoaderParameters<SCMLDataWithResources> {

  }

  /**
   * A simple binder to overcome the limitation of AssetManager. It can't have more that one data type
   * linked to a file but SCML files contain animation data and paths to images used by the animation.
   */
  public static class SCMLDataWithResources {

    private Data data;
    private Loader loader;

    public Data getData() {
      return data;
    }

    public void setData(Data data) {
      this.data = data;
    }

    public Loader getLoader() {
      return loader;
    }

    public void setLoader(Loader loader) {
      this.loader = loader;
    }
  }
}
