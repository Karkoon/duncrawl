package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by karkoon on 01.02.17.
 * Used in pathfinding a node. Contains connections.
 * It's positions and positions of its neighbours are read to create an accurate model instance.
 * Contains a list of objects that are currently occupying it to block other NPCs from moving onto it.
 */

public class DungeonSection {

    private ArrayList<Object> occupyingObjects = new ArrayList<>();
    private ArrayList<Vector3> adjacentPositions = new ArrayList<>();
    private Vector3 position;

    private EnumMap<CardinalDirection, DungeonConnection> adjacentSections = new EnumMap<>(CardinalDirection.class);

    ArrayList<Vector3> getAdjacentSectionPositions() {
        return adjacentPositions;
    }

    public EnumMap<CardinalDirection, DungeonConnection> getConnections() {
        return adjacentSections;
    }

    public DungeonConnection getConnection(CardinalDirection direction) {
        return adjacentSections.get(direction);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public ArrayList<Object> getOccupyingObjects() {
        return occupyingObjects;
    }

    public void addOccupyingObject(Object object) {
        occupyingObjects.add(object);
    }
}