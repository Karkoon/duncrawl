package ashlified.graphics;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.EnumMap;

/**
 * Created by kacper on 25.01.17.
 * Common interface for providing new models.
 */
public interface WallModelsProvider {

  EnumMap<WallModelsAccessor.WallType, Model> getNewModels(LevelTheme theme);

}
