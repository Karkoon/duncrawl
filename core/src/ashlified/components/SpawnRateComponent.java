package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 01.04.17.
 */
public class SpawnRateComponent implements Component {

    public final static int NEVER = 0;
    public final static int START = 0;


    private int chanceOfAppearing;
    private int startLevel;
    private int endLevel;
    private String levelTheme;

    public SpawnRateComponent(String levelTheme) {
        this.levelTheme = levelTheme;
    }

    public String getLevelTheme() {
        return levelTheme;
    }

    public void setLevelTheme(String levelTheme) {
        this.levelTheme = levelTheme;
    }

    public int getChanceOfAppearing() {
        return chanceOfAppearing;
    }

    public void setSpawnRate(int chanceOfAppearing) {
        this.chanceOfAppearing = chanceOfAppearing;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
    }

    public int getEndLevel() {
        return endLevel;
    }

    public void setEndLevel(int endLevel) {
        this.endLevel = endLevel;
    }
}
