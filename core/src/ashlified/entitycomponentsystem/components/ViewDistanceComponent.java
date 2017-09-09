package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 01.04.17.
 */
public final class ViewDistanceComponent implements Component, Pool.Poolable {

    private int viewDistance;

    public ViewDistanceComponent() {
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    @Override
    public void reset() {
        viewDistance = 0;
    }
}
