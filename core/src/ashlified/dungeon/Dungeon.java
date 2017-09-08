package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-20.
 * Class used for libGDX's deserialization from json files. Relevant fields are width, height, grid.
 * The rest is customizable.
 */
public class Dungeon implements Json.Serializable {

    private int width;
    private int height;
    private ArrayList<DungeonSection> grid;

    public ArrayList<DungeonSection> getGrid() {
        return grid;
    }

    @Override
    public void write(Json json) {
        json.writeValue(width);
        json.writeValue(height);
        json.writeValue(grid);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        width = json.readValue("width", int.class, jsonData);
        height = json.readValue("height", int.class, jsonData);
        grid = json.readValue("grid", ArrayList.class, DungeonSection.class, jsonData);
        for (DungeonSection section : grid) {
            section.setDungeon(this);
            determineSectionConnections(section);
        }
    }

    public DungeonSection getDungeonSectionAt(Vector3 position) {
        for (DungeonSection section : grid) {
            if (section.getPosition().equals(position)) return section;
        }
        return null;
    }

    private void determineSectionConnections(DungeonSection section) {
        EnumMap<CardinalDirection, DungeonSection> adjacentSections = section.getAdjacentSections();
        for (CardinalDirection direction : CardinalDirection.values()) {
            adjacentSections.put(direction, section);
        }
        for (Vector3 adjacentSectionPosition : section.getAdjacentSectionPositions()) {
            if (adjacentSectionPosition.x < section.getPosition().x) {
                adjacentSections.put(CardinalDirection.WEST, getDungeonSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.x > section.getPosition().x) {
                adjacentSections.put(CardinalDirection.EAST, getDungeonSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.z < section.getPosition().z) {
                adjacentSections.put(CardinalDirection.NORTH, getDungeonSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.z > section.getPosition().z) {
                adjacentSections.put(CardinalDirection.SOUTH, getDungeonSectionAt(adjacentSectionPosition));
            }
        }
    }

    public DungeonSection getRandomDungeonSection() {
        return grid.get(new Random().nextInt(grid.size()));
    }

    public DungeonSection getSpawnDungeonSection() {
        return grid.get(0);
    }

}
