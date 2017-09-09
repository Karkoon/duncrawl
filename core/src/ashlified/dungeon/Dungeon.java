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
        Array<Connection<DungeonSection>> connections = new Array<>();
        for (DungeonConnection connection : fromNode.getConnections().values()) {
            connections.add(connection);
        }
        ;
        return connections;
    }
}
