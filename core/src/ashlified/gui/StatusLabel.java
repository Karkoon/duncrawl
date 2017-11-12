package ashlified.gui;

import ashlified.entitycomponentsystem.components.HealthComponent;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StatusLabel extends Table {

    private Label label;
    private HealthComponent healthComponent;

    public StatusLabel(Skin skin, Engine engine) {
        label = new Label("", skin);
        this.healthComponent = engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get()).first().getComponent(HealthComponent.class);
        add(label);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        label.setText(Integer.toString(healthComponent.getHealth()));
    }
}
