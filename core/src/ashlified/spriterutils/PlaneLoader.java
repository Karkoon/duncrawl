package ashlified.spriterutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class PlaneLoader extends Loader<ModelInstance> implements Disposable {

    private static Model model;
    private static ModelBuilder builder = new ModelBuilder();

    public static int standardAtlasWidth = 2048;
    public static int standardAtlasHeight = 2048;
    private PixmapPacker packer;
    private HashMap<FileReference, Pixmap> pixmaps;
    private HashMap<Pixmap, Boolean> pixmapsToDispose;
    private boolean pack;
    private int atlasWidth;
    private int atlasHeight;

    private void createModel() {
        long attributes = VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates;
        model = builder.createRect(
                -5,0,0,
                5,0,0,
                5,10,0,
                -5,10,0,
                0, 0, 1,
                new Material(new BlendingAttribute()), attributes);
    }

    public PlaneLoader(Data data) {
        this(data, true);
    }

    public PlaneLoader(Data data, boolean pack) {
        this(data, standardAtlasWidth, standardAtlasHeight);
        this.pack = pack;
    }

    public PlaneLoader(Data data, int atlasWidth, int atlasHeight) {
        super(data);
        this.pack = true;
        this.atlasWidth = atlasWidth;
        this.atlasHeight = atlasHeight;
        this.pixmaps = new HashMap();
        this.pixmapsToDispose = new HashMap();
        createModel();
    }

    protected ModelInstance loadResource(FileReference ref) {
        String pathPrefix;
        if (super.root != null && !super.root.equals("")) {
            pathPrefix = super.root + File.separator;
        } else {
            pathPrefix = "";
        }

        String path = pathPrefix + this.data.getFile(ref).name;
        FileHandle f;
        switch (Gdx.app.getType().ordinal()) {
            case 1:
                f = Gdx.files.absolute(path);
                break;
            default:
                f = Gdx.files.internal(path);
        }

        if (!f.exists()) {
            throw new GdxRuntimeException("Could not find file handle " + path + "! Please check your paths.");
        } else {
            if (this.packer == null && this.pack) {
                this.packer = new PixmapPacker(this.atlasWidth, this.atlasHeight, Pixmap.Format.RGBA8888, 2, true);
            }

            Pixmap pix = new Pixmap(f);
            this.pixmaps.put(ref, pix);
            return null;
        }
    }

    protected void generatePackedModelInstances() {
        if (this.packer != null) {
            TextureAtlas tex = this.packer.generateTextureAtlas(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, false);
            Set<FileReference> keys = this.resources.keySet();

            for (FileReference ref : keys) {
                TextureRegion texReg = tex.findRegion(this.data.getFile(ref).name);
                texReg.setRegionWidth((int) this.data.getFile(ref).size.width);
                texReg.setRegionHeight((int) this.data.getFile(ref).size.height);
                model.materials.first().set(TextureAttribute.createDiffuse(texReg));

                super.resources.put(ref, new ModelInstance(model));
            }

        }
    }

    public void dispose() {
        if (this.pack && this.packer != null) {
            this.packer.dispose();
        }
        super.dispose();
    }

    protected void finishLoading() {
        Set<FileReference> refs = this.resources.keySet();

        for (FileReference ref : refs) {
            Pixmap pix = this.pixmaps.get(ref);
            this.pixmapsToDispose.put(pix, Boolean.FALSE);
            this.createPlaneModelInstance(ref, pix);
            if (this.packer != null) {
                this.packer.pack(this.data.getFile(ref).name, pix);
            }
        }

        if (this.pack) {
            this.generatePackedModelInstances();
        }

        this.disposePixmaps();
    }

    protected void createPlaneModelInstance(FileReference ref, Pixmap image) {
        Texture tex = new Texture(image);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int width = (int) this.data.getFile(ref.folder, ref.file).size.width;
        int height = (int) this.data.getFile(ref.folder, ref.file).size.height;
        TextureRegion texRegion = new TextureRegion(tex, width, height);

        model.materials.first().set(TextureAttribute.createDiffuse(texRegion));
        super.resources.put(ref, new ModelInstance(model));
        this.pixmapsToDispose.put(image, Boolean.TRUE);
    }

    protected void disposePixmaps() {
        Pixmap[] maps = new Pixmap[this.pixmapsToDispose.size()];
        this.pixmapsToDispose.keySet().toArray(maps);
        Pixmap[] var2 = maps;
        int var3 = maps.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Pixmap pix = var2[var4];

            try {
                while (this.pixmapsToDispose.get(pix)) {
                    pix.dispose();
                    this.pixmapsToDispose.put(pix, Boolean.FALSE);
                }
            } catch (GdxRuntimeException var7) {
                System.err.println("Pixmap was already disposed!");
            }
        }

        this.pixmapsToDispose.clear();
    }
}
