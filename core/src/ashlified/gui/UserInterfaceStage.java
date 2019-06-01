package ashlified.gui;

import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.signals.PlayerTurnEndSignal;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UserInterfaceStage extends Stage {

  private WindowController windowController;

  public UserInterfaceStage(Skin skin, PooledEngine engine, PlayerTurnEndSignal playerTurnEndSignal) {
    super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

    windowController = new WindowController(skin, engine, engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first(), this);

    Stack root = new Stack();
    root.setFillParent(true);

    Table uitable = new Table(skin);
    uitable.setFillParent(true);
    StatusTable statusTable = new StatusTable(skin, engine);

    uitable.add(statusTable);
    uitable.row();
    uitable.add().fill().expand();
    uitable.row();
    uitable.left().bottom();
    uitable.add(new FunctionalButtonsTable(skin, windowController)).align(Align.left).padLeft(20).padRight(20).fillX();
    uitable.row();
    uitable.add(new ControlButtonsTable(skin, engine, playerTurnEndSignal)).pad(20).expandX().fillX();

    root.add(uitable);
    addActor(root);
    setDebugAll(false);
  }

  static Button createFunctionalButton(Skin skin, final Runnable runnable) {
    Button button = new Button(skin);
    button.addListener(new ClickListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        runnable.run();
        return true;
      }
    });
    return button;
  }
}
