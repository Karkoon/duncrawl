package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-20.
 * Class used for libGDX's deserialization from json files. Relevant fields are width, height, grid.
 * The rest is customizable.
 */
public class Dungeon implements Json.Serializable, Cacheable {

    private int width;
    private int height;
    private Model dungeonModel;
    private ModelBuilder builder = new ModelBuilder();
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
        builder.begin();
        for (DungeonSection section : grid) {
            section.setDungeon(this);
        }
        dungeonModel = builder.end();
    }

    @Override
    public void cache(ModelCache cache, Environment environment) {
        for (DungeonSection section : grid) {
            section.cache(cache, environment);
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

        private static final int SCALE = 10;
        public static final float SIZE = 1f * SCALE; //square
        public static final float HEIGHT = 1.25f * SCALE;
        private static ModelBuilder builder = new ModelBuilder();
        private static Model floorModel = builder.createRect(0, 0, 0, SIZE, 0, 0, SIZE, 0, SIZE, 0, 0, SIZE, 0, -1, 0,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        private static Model wallModel = builder.createRect(0, 0, 0, SIZE, 0, 0, SIZE, HEIGHT, 0, 0, HEIGHT, 0, 0, 0, 1,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal); //// TODO: 2016-09-01 change to meshpartbuilder
        public Vector2 point; //used by json thing
        private ArrayList<Vector2> next; //used by json thing
        public ArrayList<Object> occupyingObject;
        private Dungeon dungeon;
//        private ArrayList<ModelInstance> instances = new ArrayList<>();
        private ArrayList<ModelInstance> instances = new ArrayList<>();

        @Override
        public void write(Json json) {
            point = new Vector2(point.x / SCALE, point.y / SCALE);
            for (Vector2 vector2 : next) {
                vector2.set(vector2.x / SCALE, vector2.y / SCALE);
            }
            json.writeValue(point);
            json.writeValue(next);
        }

        @Override
        public void read(Json json, JsonValue jsonData) {
            point = json.readValue("point", Vector2.class, jsonData);
            next = json.readValue("next", ArrayList.class, Vector2.class, jsonData);
            point = new Vector2(point.x * SCALE, point.y * SCALE);
            for (Vector2 vector2 : next) {
                vector2.set(vector2.x * SCALE, vector2.y * SCALE);
            }
            occupyingObject = new ArrayList<>();
            constructWallSection();
        }

        @Override
        public void cache(ModelCache cache, Environment environment) {
            for (ModelInstance modelInstance : instances) {
                cache.add(modelInstance);
            }
        }

        public Dungeon getDungeon() {
            return dungeon;
        }

        public void setDungeon(Dungeon dungeon) {
            this.dungeon = dungeon;
        }

        private void constructWallSection() { // used because of deserialization and errors with creating a proper constructWallSection
            ModelInstance floor = new ModelInstance(floorModel);
            floor.transform.translate(point.x - SIZE / 2f, 0, point.y + SIZE / 2f);
            floor.transform.rotate(Vector3.X, 180);
            instances.add(floor);
            assembleWalls(determineWallCoords());
        }


        private ArrayList<Vector2> determineWallCoords() {
            ArrayList<Vector2> possibleWallCoords = new ArrayList<Vector2>();
            possibleWallCoords.add(point.cpy().add(-SIZE, 0));
            possibleWallCoords.add(point.cpy().add(SIZE, 0));
            possibleWallCoords.add(point.cpy().add(0, -SIZE));
            possibleWallCoords.add(point.cpy().add(0, SIZE));
            ArrayList<Vector2> wallCoords = new ArrayList<Vector2>(possibleWallCoords);
            for (Vector2 possibleCoord : possibleWallCoords) {
                for (Vector2 nextCoord : next) {
                    if (possibleCoord.equals(nextCoord)) wallCoords.remove(possibleCoord);
                }
            }
            return wallCoords;
        }

        private void assembleWalls(ArrayList<Vector2> wallCoords) {
            for (Vector2 coord : wallCoords) {
                // TODO: 2016-08-27 **make me prettier**
                Vector2 differenceBetweenPointPosAndWallPos = point.cpy().sub(coord);
                float rotation = 0;
                if (differenceBetweenPointPosAndWallPos.x == SIZE) {
                    rotation = 90;
                    coord.add(SIZE / 2f, SIZE / 2f);
                } else if (differenceBetweenPointPosAndWallPos.x == -SIZE) {
                    rotation = -90;
                    coord.add(-SIZE / 2f, -SIZE / 2f);
                } else if (differenceBetweenPointPosAndWallPos.y == SIZE) {
                    rotation = 0;
                    coord.add(-SIZE / 2f, SIZE / 2f);
                } else if (differenceBetweenPointPosAndWallPos.y == -SIZE) {
                    rotation = 180;
                    coord.add(SIZE / 2f, -SIZE / 2f);
                }
                putWallAt(coord, rotation);
            }
        }

        private void putWallAt(Vector2 coord, float rotation) {
            ModelInstance instance = new ModelInstance(wallModel);
            instance.transform.translate(coord.x, 0, coord.y);
            instance.transform.rotate(Vector3.Y, rotation);
            instances.add(instance);
        }
    }
}
