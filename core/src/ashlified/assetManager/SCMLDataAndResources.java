package ashlified.assetManager;

import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Loader;


/**
 * A simple binder to overcome the limitation of AssetManager. It can't have more that one data type
 * linked to a file but SCML files contain animation data and paths to images used by the animation.
 */
public class SCMLDataAndResources {

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
