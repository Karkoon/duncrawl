package ashlified.dungeon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by karkoon on 01.02.17.
 */

public class DungeonSection implements Json.Serializable {

    private static float scale = 10f;
    private ArrayList<Object> occupyingObjects;
    private Vector2 point; //used by json thing
    private ArrayList<Vector2> next; //used by json thing
    private Dungeon dungeon;

    public ArrayList<Vector2> getNext() {
        return next;
    }

    public Vector2 getPoint() {
        return point;
    }

    public ArrayList<Object> getOccupyingObjects() {
        return occupyingObjects;
    }

    @Override
    public void write(Json json) {
        point = new Vector2(point.x / scale, point.y / scale);
        for (Vector2 vector2 : next) {
            vector2.set(vector2.x, vector2.y);
        }
        json.writeValue(point);
        json.writeValue(next);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        point = json.readValue("point", Vector2.class, jsonData);
        next = json.readValue("next", ArrayList.class, Vector2.class, jsonData);
        point = new Vector2(point.x * scale, point.y * scale);
        for (Vector2 vector2 : next) {
            vector2.set(vector2.x * scale, vector2.y * scale);
        }

        occupyingObjects = new ArrayList<>();
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
}
