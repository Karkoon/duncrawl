package ashlified.dungeon;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-20.
 * Class used for libGDX's deserialization from json files. Relevant fields are width, height, grid.
 * The rest is customizable.
 */
public class Dungeon {

    private int width;
    private int height;
    private ArrayList<DungeonSection> grid;

    public ArrayList<DungeonSection> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<DungeonSection> grid) {
        this.grid = grid;
    }

    public DungeonSection getSectionAt(Vector3 position) {
        for (DungeonSection section : grid) {
            if (section.getPosition().equals(position)) return section;
        }
        return null;
    }

    public DungeonSection getRandomDungeonSection() {
        DungeonSection randomSection;
        do {
            randomSection = grid.get(new Random().nextInt(grid.size()));
        } while (randomSection.getOccupyingObjects().size() != 0);
        return randomSection;
    }

    public DungeonSection getSpawnDungeonSection() {
        return grid.get(0);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
