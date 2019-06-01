package ashlified.gui;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

class StatusTable extends Table {

  StatusTable(final Skin skin, final PooledEngine engine) {
    super(skin);
    add(new StatusLabel(skin, engine));
  }

}
