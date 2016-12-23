package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-20.
 * Class used for libGDX's deserialization from json files. Relevant fields are width, height, grid.
 * The rest is customizable.
 */
public class Dungeon implements Json.Serializable, Cacheable {

    private int width;
    private int height;
    private ArrayList<DungeonSection> grid;

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
        }
    }

    @Override
    public void cacheModel(ModelCache cache, Environment environment) {
        for (DungeonSection section : grid) {
            section.cacheModel(cache, environment);
        }
    }

    public DungeonSection getDungeonSectionAt(Vector2 position) {
        for (DungeonSection section : grid) {
            if (section.point.equals(position)) return section;
        }
        return null;
    }

    public DungeonSection getRandomDungeonSection() {
        return grid.get(new Random().nextInt(grid.size()));
    }

    public DungeonSection getSpawnDungeonSection() {
        return grid.get(0);
    }


    /**
     * Created by @Karkoon on 2016-08-30.
     */
    public static class DungeonSection implements Json.Serializable, Cacheable {

        public Vector2 point; //used by json thing
        public ArrayList<Object> occupyingObject;
        private ArrayList<Vector2> next; //used by json thing
        private Dungeon dungeon;
        private ModelInstance modelInstance;

        @Override
        public void write(Json json) {
            point = new Vector2(point.x, point.y);
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
            point = new Vector2(point.x, point.y);
            for (Vector2 vector2 : next) {
                vector2.set(vector2.x, vector2.y);
            }
            occupyingObject = new ArrayList<>();

        }

        @Override
        public void cacheModel(ModelCache cache, Environment environment) {
            cache.add(modelInstance);
        }

        public Dungeon getDungeon() {
            return dungeon;
        }

        public void setDungeon(Dungeon dungeon) {
            this.dungeon = dungeon;
        }

        private void determineSectionType() {
            boolean hasNorthBorder = true;
            boolean hasSouthBorder = true;
            boolean hasWestBorder = true;
            boolean hasEastBorder = true;

            for (Vector2 nextSection : next) {
                Vector2 differenceBetweenSectionPositionAndNextSectionPosition = point.cpy().sub(nextSection);
                if (differenceBetweenSectionPositionAndNextSectionPosition.x == 1) {
                    hasWestBorder = false;
                } else if (differenceBetweenSectionPositionAndNextSectionPosition.x == -1) {
                    hasEastBorder = false;
                } else if (differenceBetweenSectionPositionAndNextSectionPosition.y == 1) {
                    hasNorthBorder = false;
                } else if (differenceBetweenSectionPositionAndNextSectionPosition.y == -1) {
                    hasSouthBorder = false;
                }
            }

            EnumMap<Assets.WallType, Model> themedModels = Assets.getWallModels();

       /*     if (hasEastBorder && hasNorthBorder && hasSouthBorder && hasWestBorder) {
                modelInstance = new ModelInstance(themedModels.get(Assets.WallType.NO_SIDES), new Vector3(point.x, 0, point.y));
            } else if (!hasNorthBorder && !hasSouthBorder && !hasEastBorder && hasWestBorder
                    || !hasNorthBorder && !hasSouthBorder && hasEastBorder && !hasWestBorder
                    || !hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder
                    || hasNorthBorder && !hasSouthBorder && !hasEastBorder && !hasWestBorder) {
                modelInstance = new ModelInstance(themedModels.get(Assets.WallType.ONE_SIDE), new Vector3(point.x, 0, point.y));
            } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder
                    || hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
                modelInstance = new ModelInstance(themedModels.get(Assets.WallType.TWO_SIDES), new Vector3(point.x, 0, point.y));
            } else if (!hasNorthBorder && hasSouthBorder && !hasEastBorder
                    || !hasNorthBorder && hasSouthBorder && !hasWestBorder
                    || hasNorthBorder && !hasSouthBorder && !hasEastBorder
                    || hasNorthBorder && !hasSouthBorder && !hasWestBorder) {
                modelInstance = new ModelInstance(themedModels.get(Assets.WallType.CORNER), new Vector3(point.x, 0, point.y));
            }
        }*/
    }
}
