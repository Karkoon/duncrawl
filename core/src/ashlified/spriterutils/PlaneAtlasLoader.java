package ashlified.spriterutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;

public class PlaneAtlasLoader extends Loader<ModelInstance> {

    static private int count = 1;
    private Model planeModel;
    private TextureAtlas atlas;

    public PlaneAtlasLoader(Data data, AssetManager assetManager) {
        super(data);
        planeModel = assetManager.get("npc/untitled.g3db", Model.class);
        atlas = assetManager.get("npc/npcs.atlas", TextureAtlas.class);
    }

    @Override
    protected ModelInstance loadResource(FileReference ref) {
        String fileName = data.getFile(ref.folder, ref.file).name;
        TextureRegion texRegion = atlas.findRegion(fileName);
        Material material = planeModel.materials.first();
        material.set(TextureAttribute.createDiffuse(texRegion), new FloatAttribute(FloatAttribute.AlphaTest, 0.5f), new BlendingAttribute());
        Gdx.app.log("da", "added instance " + count++ + " using " + fileName);
        return new ModelInstance(planeModel);
    }
}
