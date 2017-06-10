package ashlified.systems;

import ashlified.dungeon.Dungeon;
import ashlified.factories.NPCFactory;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;

/**
 * Created by karkoon on 25.03.17.
 */
public class NPCSystem extends EntitySystem {

    private Dungeon dungeon;

    public NPCSystem(Dungeon dungeon) {
        super();
        this.dungeon = dungeon;
    }

    private void createNPCs() {
        NPCFactory factory = new NPCFactory(getEngine());
        for (int i = 0; i < 50; i++) {
            try {
                factory.createEnemyNPC("'Hell knight'", dungeon);
                factory.createEnemyNPC("'Snorg'", dungeon);
            } catch (Exception e) {
                e.printStackTrace();
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createNPCs();
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
