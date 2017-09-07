package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.EnumMap;

public class DungeonSerializer implements Json.Serializer<Dungeon> {

    private Dungeon dungeon;

    @Override
    public void write(Json json, Dungeon object, Class knownType) {
        json.writeValue(dungeon.getWidth());
        json.writeValue(dungeon.getHeight());
        json.writeValue(dungeon.getGrid());
    }

    @Override
    public Dungeon read(Json json, JsonValue jsonData, Class type) {
        dungeon = new Dungeon();
        dungeon.setGrid(json.readValue("grid", ArrayList.class, DungeonSection.class, jsonData));
        for (DungeonSection section : dungeon.getGrid()) {
            determineSectionConnections(section);
        }
        return dungeon;
    }

    private void determineSectionConnections(DungeonSection section) {
        EnumMap<CardinalDirection, DungeonSection> adjacentSections = section.getAdjacentSections();
        for (CardinalDirection direction : CardinalDirection.values()) {
            adjacentSections.put(direction, section);
        }
        for (Vector3 adjacentSectionPosition : section.getAdjacentSectionPositions()) {
            if (adjacentSectionPosition.x < section.getPosition().x) {
                adjacentSections.put(CardinalDirection.WEST, dungeon.getSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.x > section.getPosition().x) {
                adjacentSections.put(CardinalDirection.EAST, dungeon.getSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.z < section.getPosition().z) {
                adjacentSections.put(CardinalDirection.NORTH, dungeon.getSectionAt(adjacentSectionPosition));
            } else if (adjacentSectionPosition.z > section.getPosition().z) {
                adjacentSections.put(CardinalDirection.SOUTH, dungeon.getSectionAt(adjacentSectionPosition));
            }
        }
    }

    public static class DungeonSectionSerializer implements Json.Serializer<DungeonSection> {

        private float scale = 10f; // in the data each section has 1 unit size, multiplying gives a more reasonable number and making it here allows for a common interface

        @Override
        public void write(Json json, DungeonSection object, Class knownType) {
            Vector2 point = new Vector2(changeToVector2(object.getPosition()));
            ArrayList<Vector2> next = new ArrayList<>();
            for (Vector3 adjacentPosition : object.getAdjacentSectionPositions()) {
                next.add(changeToVector2(adjacentPosition));
            }
            json.writeValue(point);
            json.writeValue(next);
        }

        @Override
        public DungeonSection read(Json json, JsonValue jsonData, Class type) {
            Vector2 jsonPoint = json.readValue("point", Vector2.class, jsonData);
            ArrayList<Vector2> jsonNext = json.readValue("next", ArrayList.class, Vector2.class, jsonData);

            DungeonSection section = new DungeonSection();
            section.setPosition(new Vector3(changeToVector3(jsonPoint)));

            for (Vector2 adjacentPosition : jsonNext) {
                section.getAdjacentSectionPositions().add(changeToVector3(adjacentPosition));
            }

            return section;
        }

        private Vector3 changeToVector3(Vector2 position) {
            return new Vector3(position.x * scale, 0, position.y * scale);
        }

        private Vector2 changeToVector2(Vector3 position) {
            return new Vector2(position.x / scale, position.z / scale);
        }
    }
}
