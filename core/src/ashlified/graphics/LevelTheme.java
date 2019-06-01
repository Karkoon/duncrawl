package ashlified.graphics;

import com.badlogic.gdx.graphics.Color;

/**
 * Describes a level theme.
 */
public class LevelTheme {

  public final static int NEVER = 0;
  public final static int START = 0;

  private String name;
  private Color color;
  private int spawnRate;
  private int startLevel;
  private int endLevel;

  public String getName() {
    return name;
  }

  public Color getColor() {
    return color;
  }

  public int getSpawnRate() {
    return spawnRate;
  }

  public int getStartLevel() {
    return startLevel;
  }

  public int getEndLevel() {
    return endLevel;
  }
}
