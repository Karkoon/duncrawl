package ashlified.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import static ashlified.gui.UserInterfaceStage.createFunctionalButton;


class FunctionalButtonsTable extends Table {


  FunctionalButtonsTable(final Skin skin, WindowController controller) {
    super(skin);
    Button inventoryButton = createFunctionalButton(skin, controller::showInventory);
    add(inventoryButton).size(100, 100).left();
    add().expandX();
    Button dummy = new Button(skin); // dummies for looks
    add(dummy).size(100, 100).align(Align.right);
    row().padTop(20);
    Button dummy2 = new Button(skin);
    add(dummy2).size(100, 100).align(Align.right);
    add().expandX();
    Button dummy3 = new Button(skin);
    add(dummy3).size(100, 100).align(Align.right);
  }
}
