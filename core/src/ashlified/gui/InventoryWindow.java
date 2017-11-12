package ashlified.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class InventoryWindow extends Window {

    InventoryWindow(Skin skin) {
        super("ttile", skin);
        setSize(300, 300);
        align(Align.center);
        setSize(100, 100);
        Button button = new Button(skin);
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InventoryWindow.super.getParent().removeActor(InventoryWindow.this);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        add(button).size(100, 100);
    }
}
