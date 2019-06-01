package ashlified.loading.assetmanagerloaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Reads text from file.
 */
public class StringLoader extends AsynchronousAssetLoader<String, StringLoader.StringLoaderParameter> {

  private String text;

  public StringLoader(FileHandleResolver resolver) {
    super(resolver);
  }

  @Override
  public void loadAsync(AssetManager manager, String fileName, FileHandle file, StringLoaderParameter parameter) {
    text = null;
    text = file.readString();
  }

  @Override
  public String loadSync(AssetManager manager, String fileName, FileHandle file, StringLoaderParameter parameter) {
    String text = this.text;
    this.text = null;
    return text;
  }

  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, StringLoaderParameter parameter) {
    return null;
  }

  public static class StringLoaderParameter extends AssetLoaderParameters<String> {

  }
}
