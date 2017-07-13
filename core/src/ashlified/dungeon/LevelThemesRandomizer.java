package ashlified.dungeon;

import ashlified.util.RandomNumber;

import java.util.ArrayList;

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
        int roll = RandomNumber.nextInt(sum);
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
