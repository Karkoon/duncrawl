package ashlified.graphics.spriterutils;

import ashlified.AssetPaths;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.FlushablePool;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;

/**
 * Loads spriter images and creates a Pool of ModelInstances for each type of animation keyframes.
 */
public class PoolPlaneAtlasLoader extends Loader<FlushablePool<ModelInstance>> {

    private Model planeModel;
    private TextureAtlas atlas;

    public PoolPlaneAtlasLoader(Data data, AssetManager assetManager) {
        super(data);
        planeModel = assetManager.get(AssetPaths.NPC_MODEL, Model.class);
        atlas = assetManager.get(AssetPaths.NPC_ATLAS, TextureAtlas.class);
    }

    @Override
    protected FlushablePool<ModelInstance> loadResource(FileReference ref) {
        final String fileName = data.getFile(ref.folder, ref.file).name;
        final TextureRegion texRegion = atlas.findRegion(fileName);
        final Material material = planeModel.materials.first();
        material.set(ColorAttribute.createDiffuse(0.8f, 0.8f, 0.8f, 1f), new FloatAttribute(FloatAttribute.AlphaTest, 0.5f), new BlendingAttribute());
        return new FlushablePool<ModelInstance>() {
            @Override
            protected ModelInstance newObject() {
                material.set(TextureAttribute.createDiffuse(texRegion));
                return new ModelInstance(planeModel);
            }
        };
    }
}
