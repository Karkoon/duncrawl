package ashlified.systems;

import ashlified.components.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

/**
 * Created by karkoon on 01.04.17.
 */
public class NPCFactory {

    private final PooledEngine engine;
    private final EnemyNPCBlueprint blueprints = new EnemyNPCBlueprint();

    public NPCFactory(PooledEngine engine) {
        this.engine = engine;
    }

    public Entity createEnemyNPC(String enemyNPCname) {
        StatsComponent stats = engine.createComponent(StatsComponent.class);
        PositionComponent position = engine.createComponent(PositionComponent.class);
        SpeedComponent speed = engine.createComponent(SpeedComponent.class);
        InventoryComponent inventory = engine.createComponent(InventoryComponent.class);
        AnimationsComponent animations = engine.createComponent(AnimationsComponent.class);
        ArmorComponent armor = engine.createComponent(ArmorComponent.class);
        AttackComponent attack = engine.createComponent(AttackComponent.class);
        ViewDistanceComponent ViewDistance = engine.createComponent(ViewDistanceComponent.class);
        NameComponent name = engine.createComponent(NameComponent.class);

        Entity entity = engine.createEntity();

    }

    private class EnemyNPCBlueprint {

        String name;
        int strenght;
        int wisdom;
        int dexterity;
        int maxHealth;
        int armor;
        int levelOfFirstAppearance;
        int levelOfLastAppearance;
        int rateOfAppearance;
        String themeOfPlaceOfAppearance;
        int maxNumberOfItems;
        int viewDistance;

        void applyBlueprint()
    }
}
