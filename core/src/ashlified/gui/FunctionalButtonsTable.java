package ashlified.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;

import static ashlified.gui.UserInterface.createFunctionalButton;


class FunctionalButtonsTable extends Table {

  private static final Value BUTTON_SIZE = new Value.Fixed(45);

  FunctionalButtonsTable(final Skin skin, WindowController controller) {
    super(skin);
    Button inventoryButton = createFunctionalButton(skin, controller::showInventory);
    add(inventoryButton).size(BUTTON_SIZE).left();
    add().expandX();
    Button dummy = new Button(skin); // dummies for looks
    add(dummy).size(BUTTON_SIZE).align(Align.right);
    row().padTop(5);
    Button dummy2 = new Button(skin);
    add(dummy2).size(BUTTON_SIZE).align(Align.right);
    add().expandX();
    Button dummy3 = new Button(skin);
    add(dummy3).size(BUTTON_SIZE).align(Align.right);
  }
}
