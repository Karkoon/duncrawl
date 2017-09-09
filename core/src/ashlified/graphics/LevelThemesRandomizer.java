package ashlified.graphics;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Randomly chooses on a level theme. It weights its choice using the spawn rate of the levels.
 */
public class LevelThemesRandomizer {

    private ArrayList<LevelTheme> levelThemes;

    public LevelThemesRandomizer(ArrayList<LevelTheme> themesToRandomlySelectFrom) {
        if (themesToRandomlySelectFrom == null || themesToRandomlySelectFrom.isEmpty())
            throw new IllegalArgumentException("The list of themes cannot be empty or null");
        levelThemes = themesToRandomlySelectFrom;
    }

    public LevelTheme randomlySelectLevelTheme() {
        int sum = 0;
        for (LevelTheme theme : levelThemes) {
            sum += theme.getSpawnRate();
        }
        int roll = MathUtils.random(sum);
        int partial = 0;
        for (LevelTheme theme : levelThemes) {
            partial += theme.getSpawnRate();
            if (roll <= partial) {
                return theme;
            }
        }
        return null;
    }

}
