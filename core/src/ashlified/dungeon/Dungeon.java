package ashlified.dungeon;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-20.
 * Used for pathfinding and managing dungeon sections.
 */
public class Dungeon implements IndexedGraph<DungeonSection> {

    private int width;
    private int height;
    private ArrayList<DungeonSection> grid;

    public ArrayList<DungeonSection> getGrid() {
        return grid;
    }

    void setGrid(ArrayList<DungeonSection> grid) {
        this.grid = grid;
    }

    DungeonSection getSectionAt(Vector3 position) {
        for (DungeonSection section : grid) {
            if (section.getPosition().equals(position)) return section;
        }
        return null;
    }

    public DungeonSection getRandomDungeonSection() {
        DungeonSection randomSection;
        boolean isValid;
        do {
            randomSection = grid.get(new Random().nextInt(grid.size()));
            int numberOfConnections = randomSection.getConnections().size;
            isValid = numberOfConnections != 2 || numberOfConnections != 3 || numberOfConnections != 6;
            for (int i = 0; i < randomSection.getConnections().size; i++) {
                Connection<DungeonSection> connection = randomSection.getConnections().get(i);
                isValid &= (connection.getToNode().getConnections().size != 2);
            }
        } while (randomSection.getOccupyingEntities().size() != 0 || !isValid);
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

    @Override
    public int getIndex(DungeonSection node) {
        return grid.indexOf(node);
    }

    @Override
    public int getNodeCount() {
        return grid.size();
    }

    @Override
    public Array<Connection<DungeonSection>> getConnections(DungeonSection fromNode) {
        return fromNode.getConnections();
    }
}
