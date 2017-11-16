package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.*;
import ashlified.graphics.spriterutils.AnimationID;
import ashlified.graphics.spriterutils.SpriterAnimationController;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.brashmonkey.spriter.Animation;

public class AttackSystem extends IteratingSystem {

    private ComponentMapper<AttackComponent> attackMapper = ComponentMapper.getFor(AttackComponent.class);
    private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);
    private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    private ComponentMapper<SpriterModelComponent> spriterMapper = ComponentMapper.getFor(SpriterModelComponent.class);

    AttackSystem() {
        super(Family.all(AttackComponent.class,
                StatsComponent.class,
                HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity enemy = attackMapper.get(entity).getEnemy();
        HealthComponent enemyHealth = healthMapper.get(enemy);
        int entityStrength = statsMapper.get(entity).getStrength();
        enemyHealth.setHealth(enemyHealth.getHealth() - entityStrength);

        if (spriterMapper.has(entity)) {
            spriterMapper.get(entity).getSpriterAnimationController().attackEvent();
        }

        if (spriterMapper.has(enemy)) {
            SpriterAnimationController animationController = spriterMapper.get(enemy).getSpriterAnimationController();
            Animation animation = animationController.getPlayer().getAnimation();
            if (enemyHealth.getHealth() <= 0) {
                animationController.dieEvent();
            } else if (animation.id == AnimationID.DAMAGED.getId()){
                animationController.damagedEvent();
            }
        }

        entity.remove(AttackComponent.class);
    }
}
