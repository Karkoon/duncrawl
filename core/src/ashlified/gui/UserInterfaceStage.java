package ashlified.gui;

import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UserInterfaceStage extends Stage {

    Table root;


    public UserInterfaceStage(Skin skin, Engine engine, TurnEndSignal turnEndSignal) {
        super(new FitViewport(160 * 10, 90 * 10));
        root = new Table(skin);
        root.defaults();
        root.left().bottom();
        root.setFillParent(true);
        ButtonsTable buttonsTable = new ButtonsTable(skin, engine, turnEndSignal);
        root.add();
        root.row();
        root.add(buttonsTable).pad(20);
        root.add(new StatusLabel(skin, engine));
        addActor(root);
        setDebugAll(true);
    }


    @Override
    public void act() {
        super.act();
    }
}
