package ashlified.entitycomponentsystem.signals;


import ashlified.entitycomponentsystem.entitysystems.NpcAiSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Signal;

/**
 * Signals the end of a turn. (Triggered by movement, attack etc.).
 */
public class PlayerTurnEndSignal extends Signal<EntitySystem> {
  public PlayerTurnEndSignal(Engine engine) {
    super();
    add(engine.getSystem(NpcAiSystem.class));
  }
}
