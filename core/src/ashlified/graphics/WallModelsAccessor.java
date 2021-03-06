package ashlified.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.EnumMap;

/**
 * Created by kacper on 25.12.16.
 * Gives access to models.
 */
public class WallModelsAccessor {

  private final EnumMap<WallType, Model> models;

  public WallModelsAccessor(LevelTheme theme, AssetManager manager) {
    WallModelsProvider provider = new RuntimeCreatedWallModelsProvider(manager);
    this.models = provider.getNewModels(theme);
  }

  public Model get(WallType key) {
    return models.get(key);
  }

  /**
   * Describes different kinds of wall models.
   */
  public enum WallType {
    TWO_SIDES, CORNER, ONE_SIDE, NO_SIDES
  }
}