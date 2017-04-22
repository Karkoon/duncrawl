package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

/**
 * Created by karkoon on 22.04.17.
 */
public final class DecalComponent implements Component {

    private Decal decal;

    public Decal getDecal() {
        return decal;
    }

    public void setDecal(Decal decal) {
        this.decal = decal;
    }
}
