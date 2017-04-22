package ashlified.systems;

import ashlified.components.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCSystem extends EntitySystem {

    public NPCSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing();
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
    }

    @Override
    public Engine getEngine() {
        return super.getEngine();
    }
}
