package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by karkoon on 01.02.17.
 */

public class DungeonSection {

    private ArrayList<Object> occupyingObjects = new ArrayList<>();
    private ArrayList<Vector3> adjacentPositions = new ArrayList<>();
    private Vector3 position;

    private EnumMap<CardinalDirection, DungeonSection> adjacentSections = new EnumMap<>(CardinalDirection.class);

    ArrayList<Vector3> getAdjacentSectionPositions() {
        return adjacentPositions;
    }

    EnumMap<CardinalDirection, DungeonSection> getAdjacentSections() {
        return adjacentSections;
    }

    public DungeonSection getAdjacentSection(CardinalDirection direction) {
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