package ashlified.entitycomponentsystem.signals;


import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Signal;

/**
 * Signals the end of a turn. (Triggered by movement, attack etc.).
 * Doesn't need to hold any data or do anything. It's bad design.
 */
public class PlayerTurnEndSignal extends Signal<EntitySystem> {

}
