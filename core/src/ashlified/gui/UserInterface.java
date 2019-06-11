package ashlified.gui;

import ashlified.entitycomponentsystem.components.PlayerComponent;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UserInterface {

  // one of the standard resolutions for a 16:9 screen
  public static final int STAGE_WIDTH = 848;
  public static final int STAGE_HEIGHT = 480;

  private WindowController windowController;
  private Skin skin;
  private PooledEngine engine;
  private Stage stage;

  public UserInterface(Skin skin, PooledEngine engine) {
    this.stage = new Stage(new FitViewport(STAGE_WIDTH, STAGE_HEIGHT));
    this.skin = skin;
    this.engine = engine;

    windowController = new WindowController(skin, engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first(), stage);

    initializeComponents();
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

  public Stage getStage() {
    return stage;
  }

  private void initializeComponents() {

    Stack root = new Stack();
    root.setFillParent(true);

    Table uitable = new Table(skin);
    uitable.setFillParent(true);
    StatusTable statusTable = new StatusTable(skin, engine);
    uitable.add(statusTable);
    uitable.row();
    uitable.add().fill().expand();
    uitable.row().padBottom(70);
    uitable.left().bottom();
    uitable.add(new FunctionalButtonsTable(skin, windowController)).align(Align.left).padLeft(5).padRight(5).fillX();
    uitable.row();
    uitable.add(new ControlButtonsTable(skin, engine)).pad(5).expandX().fillX();
    root.add(uitable);

    stage.addActor(root);
  }

  public void update(int width, int height) {
    stage.getViewport().update(width, height);
  }

  public void process(float delta) {
    stage.draw();
    stage.act(delta);
  }
}
