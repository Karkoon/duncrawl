package ashlified.gui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class WindowController {

  private CharacterWindow inventory;

  WindowController(Skin skin, Entity player, Stage stage) {
    inventory = new CharacterWindow(skin, player);
    inventory.setVisible(false);
    stage.addActor(inventory);
    inventory.setPosition(stage.getWidth() / 2 - inventory.getWidth() / 2, stage.getHeight() / 2 - inventory.getHeight() / 2);
  }

  void showInventory() {
    inventory.setVisible(true);
    inventory.setZIndex(10);
  }
}
