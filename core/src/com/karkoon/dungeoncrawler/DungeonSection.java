package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;

import java.util.ArrayList;

/**
 * Created by karkoon on 01.02.17.
 */

public class DungeonSection implements Json.Serializable, Cacheable {

    private static float scale = 10f;
    private static float size = 10f;
    private static float height = 15f;
    private static WallModelsAccessor models = Assets.getWallModelsAccessor();
    private ArrayList<Object> occupyingObjects;
    private Vector2 point; //used by json thing
    private float rotation = 0;
    private Vector2 correction;
    private ArrayList<Vector2> next; //used by json thing
    private Dungeon dungeon;
    private ModelInstance modelInstance;

    public static float getSize() {
        return size;
    }

    public static float getHeight() {
        return height;
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
        modelInstance = createModelInstance();

    }

    @Override
    public void cacheModel(ModelCache cache) {
        cache.add(modelInstance);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    private ModelInstance createModelInstance() {
        correction = new Vector2(0, 0);
        ModelInstance instance = new ModelInstance(models.get(determineSectionType()));
        instance.transform.translate(point.x - 5 - correction.x, 0, point.y - 5 - correction.y);
        instance.transform.rotate(Vector3.Y, rotation);
        rotation = 0;
        return instance;
    }

    private WallModelsAccessor.WallType determineSectionType() {
        boolean hasNorthBorder = true;
        boolean hasSouthBorder = true;
        boolean hasWestBorder = true;
        boolean hasEastBorder = true;

        for (Vector2 nextSection : next) {
            Vector2 differenceBetweenSectionPositionAndNextSectionPosition = point.cpy().sub(nextSection);
            if (differenceBetweenSectionPositionAndNextSectionPosition.x == 10) {
                hasWestBorder = false;
            } else if (differenceBetweenSectionPositionAndNextSectionPosition.x == -10) {
                hasEastBorder = false;
            } else if (differenceBetweenSectionPositionAndNextSectionPosition.y == 10) {
                hasNorthBorder = false;
            } else if (differenceBetweenSectionPositionAndNextSectionPosition.y == -10) {
                hasSouthBorder = false;
            }
        }

        if (!hasNorthBorder && !hasSouthBorder && !hasEastBorder && hasWestBorder) {
            rotation = 90;
            correction.y = -size;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && !hasWestBorder) {
            correction.x = -size;
            rotation = -90;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            correction.y = -size;
            correction.x = -size;
            rotation = 180;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (hasNorthBorder && !hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            correction.y = 0;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && hasWestBorder) {
            rotation = 90;
            correction.y = -size;
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (!hasNorthBorder && hasSouthBorder && hasWestBorder && !hasEastBorder) {
            correction.y = -size;
            rotation = 90;
            return WallModelsAccessor.WallType.CORNER;
        } else if (!hasNorthBorder && hasSouthBorder && !hasWestBorder && hasEastBorder) {
            correction.x = -size;
            correction.y = -size;
            rotation = 180;
            return WallModelsAccessor.WallType.CORNER;
        } else if (hasNorthBorder && !hasSouthBorder && hasWestBorder && !hasEastBorder) {
            return WallModelsAccessor.WallType.CORNER;
        } else if (hasNorthBorder && !hasSouthBorder && !hasWestBorder && hasEastBorder) {
            correction.x = -size;
            rotation = 270;
            return WallModelsAccessor.WallType.CORNER;
        }
        return WallModelsAccessor.WallType.NO_SIDES;
    }
}
