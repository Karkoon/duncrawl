package ashlified.entitycomponentsystem.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class ItemIconComponent implements Component, Pool.Poolable {

  private TextureRegion icon;

  public TextureRegion getIcon() {
    return icon;
  }

  public void setIcon(TextureRegion icon) {
    this.icon = icon;
  }

  @Override
  public void reset() {
    icon = null;
  }
}
