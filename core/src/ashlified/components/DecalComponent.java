package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 22.04.17.
 */
public final class DecalComponent implements Component, Pool.Poolable {

    private Decal decal;

    public Decal getDecal() {
        return decal;
    }

    public void setDecal(Decal decal) {
        this.decal = decal;
    }

    @Override
    public void reset() {
        decal = null;
    }
}
