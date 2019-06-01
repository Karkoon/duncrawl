package ashlified.gui;

import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StatusLabel extends Table implements Listener<HealthComponent> {

  private Label label;

  StatusLabel(Skin skin, Engine engine) {
    HealthComponent healthComponent = engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get()).first().getComponent(HealthComponent.class);
    healthComponent.add(this);
    label = new Label("", skin);
    updateHealth(healthComponent);
    add(label);
  }

  @Override
  public void receive(Signal<HealthComponent> signal, HealthComponent object) {
    updateHealth(object);
  }

  private void updateHealth(HealthComponent health) {
    label.setText(health.getHealth() + " / " + health.getMaxHealth());
  }
}
