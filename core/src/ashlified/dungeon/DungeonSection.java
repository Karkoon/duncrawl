package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by karkoon on 01.02.17.
 */

public class DungeonSection implements Json.Serializable {

    private static float scale = 10f; // in the data each section has 1 unit size, multiplying gives a more reasonable number and making it here allows for a common interface
    private ArrayList<Object> occupyingObjects;
    private Vector3 position;
    private ArrayList<Vector3> adjacentPositions;
    private Dungeon dungeon;

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

    public ArrayList<Object> getOccupyingObjects() {
        return occupyingObjects;
    }

    public void addOccupyingObject(Object object) {
        occupyingObjects.add(object);
    }

    @Override
    public void write(Json json) {
        Vector2 point = new Vector2(this.position.x / scale, this.position.z / scale);
        ArrayList<Vector2> next = new ArrayList<>();
        for (Vector3 adjacentPosition : this.adjacentPositions) {
            next.add(new Vector2(adjacentPosition.x / scale, adjacentPosition.z / scale));
        }
        json.writeValue(point);
        json.writeValue(next);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        Vector2 jsonPoint = json.readValue("point", Vector2.class, jsonData);
        ArrayList<Vector2> jsonNext = json.readValue("next", ArrayList.class, Vector2.class, jsonData);
        position = new Vector3(jsonPoint.x * scale, 0, jsonPoint.y * scale);
        adjacentPositions = new ArrayList<>();
        for (Vector2 adjacentPositionXY : jsonNext) {
            adjacentPositions.add(new Vector3(adjacentPositionXY.x * scale, 0, adjacentPositionXY.y * scale));
        }

        occupyingObjects = new ArrayList<>();
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

}