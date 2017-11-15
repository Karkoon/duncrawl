package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by karkoon on 25.03.17.
 */
public final class AttackComponent implements Component, Pool.Poolable {

    public Entity getEnemy() {
        return enemy;
    }

    public void setEnemy(Entity enemy) {
        this.enemy = enemy;
    }

    private Entity enemy;

    @Override
    public void reset() {
        this.enemy = null;
    }
}
