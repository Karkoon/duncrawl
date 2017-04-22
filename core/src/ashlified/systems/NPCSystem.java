package ashlified.systems;

import ashlified.components.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCSystem extends EntitySystem {

    public NPCSystem(int priority) {
        super(priority);

        add(new StatsComponent());
        add(new PositionComponent());
        add(new SpeedComponent());
        add(new InventoryComponent());
        add(new AnimationsComponent());
        add(new ArmorComponent());
        add(new AttackComponent());
        add(new BehaviorComponent());
        add(new ViewDistanceComponent());
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
