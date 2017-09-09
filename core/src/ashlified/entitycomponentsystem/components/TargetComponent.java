package ashlified.entitycomponentsystem.components;

import ashlified.dungeon.DungeonSection;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TargetComponent implements Component, Pool.Poolable {

    private DungeonSection target;

    public DungeonSection getTarget() {
        return target;
    }

    public void setTarget(DungeonSection target) {
        this.target = target;
    }

    @Override
    public void reset() {
        target = null;
    }
}
