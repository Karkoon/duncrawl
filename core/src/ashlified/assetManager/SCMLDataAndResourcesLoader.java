package ashlified.assetManager;

import ashlified.spriterutils.PlaneAtlasLoader;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.SCMLReader;

public class SCMLDataAndResourcesLoader extends AsynchronousAssetLoader<SCMLDataAndResources, SCMLDataAndResourcesLoader.DataAndResourcesParameter> {

    private SCMLDataAndResources dataAndResources;

    public SCMLDataAndResourcesLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, DataAndResourcesParameter parameter) {
        dataAndResources = null;
        dataAndResources = new SCMLDataAndResources();
        dataAndResources.setData(null);
        dataAndResources.setData(new SCMLReader(file.read()).getData());
        dataAndResources.setLoader(null);
        dataAndResources.setLoader(new PlaneAtlasLoader(dataAndResources.getData(), manager));
        dataAndResources.getLoader().load(file.file());
    }

    @Override
    public SCMLDataAndResources loadSync(AssetManager manager, String fileName, FileHandle file, DataAndResourcesParameter parameter) {
        SCMLDataAndResources dataAndResources = this.dataAndResources;
        this.dataAndResources = null;
        return dataAndResources;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, DataAndResourcesParameter parameter) {
        Array<AssetDescriptor> assetDescriptors = new Array<>();
        assetDescriptors.add(new AssetDescriptor<>("npc/untitled.g3db", Model.class));
        assetDescriptors.add(new AssetDescriptor<>("npc/npcs.atlas", TextureAtlas.class));
        return assetDescriptors;
    }

    static public class DataAndResourcesParameter extends AssetLoaderParameters<SCMLDataAndResources> {

    }
}
