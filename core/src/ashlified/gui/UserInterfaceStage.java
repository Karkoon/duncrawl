package ashlified.gui;

import ashlified.entitycomponentsystem.signals.PlayerTurnEndSignal;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UserInterfaceStage extends Stage {


    public UserInterfaceStage(Skin skin, Engine engine, PlayerTurnEndSignal playerTurnEndSignal) {
        super(new FitViewport(160 * 10, 90 * 10));
        Table root = new Table(skin);
        root.defaults();
        root.left().bottom();
        root.setFillParent(true);
        ButtonsTable buttonsTable = new ButtonsTable(skin, engine, playerTurnEndSignal);
        root.add();
        root.row();
        root.add(buttonsTable).pad(20);
        root.add(new StatusLabel(skin, engine));
        addActor(root);
        setDebugAll(true);
    }
}
