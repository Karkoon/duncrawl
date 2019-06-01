package ashlified.entitycomponentsystem.entitysystems;

import ashlified.entitycomponentsystem.components.AttackActionComponent;
import ashlified.entitycomponentsystem.components.AttackComponent;
import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.StatsComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AttackSystem extends IteratingSystem {

  private ComponentMapper<AttackComponent> attackMapper = ComponentMapper.getFor(AttackComponent.class);
  private ComponentMapper<StatsComponent> statsMapper = ComponentMapper.getFor(StatsComponent.class);
  private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
  private ComponentMapper<AttackActionComponent> attackReactionMapper = ComponentMapper.getFor(AttackActionComponent.class);


  AttackSystem() {
    super(Family.all(
      AttackComponent.class,
      StatsComponent.class,
      HealthComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    Entity enemy = attackMapper.get(entity).getEnemy();
    HealthComponent enemyHealth = healthMapper.get(enemy);

    int entityStrength = statsMapper.get(entity).getStrength();
    enemyHealth.setHealth(enemyHealth.getHealth() - entityStrength);

    AttackActionComponent attackerAction = attackReactionMapper.get(entity);
    attackerAction.getAction().run();

    AttackActionComponent enemyReaction = attackReactionMapper.get(enemy);
    enemyReaction.getReaction().run();

    entity.remove(AttackComponent.class);
  }
}
