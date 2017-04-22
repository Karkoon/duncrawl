package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 01.04.17.
 */
public final class ViewDistanceComponent implements Component {

    private int viewDistance;

    public ViewDistanceComponent() {
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }
}
