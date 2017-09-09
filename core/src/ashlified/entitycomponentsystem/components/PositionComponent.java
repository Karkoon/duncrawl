package ashlified.entitycomponentsystem.components;

import ashlified.dungeon.DungeonSection;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class PositionComponent implements Component, Pool.Poolable {

    private Vector3 position;
    private DungeonSection occupiedSection;

    public DungeonSection getOccupiedSection() {
        return occupiedSection;
    }

    public void setOccupiedSection(DungeonSection occupiedSection) {
        this.occupiedSection = occupiedSection;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position.cpy();
    }

    @Override
    public void reset() {
        position = null;
        occupiedSection = null;
    }
}
