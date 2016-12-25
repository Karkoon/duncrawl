package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;

/**
 * Created by Pc on 2016-09-10.
 * Used with asset manager to give easy access to files.
 * Contains game-generated models.
 */
public class Assets {

    private static ObjectMap<Theme, EnumMap<WallType, Model>> themedModels = new ObjectMap<>();

    public static EnumMap<WallType, Model> getWallModels() {
        if (themedModels.size > 0) {
            ObjectMap.Keys<Theme> themes = themedModels.keys();
            int sum = 0;
            for (Theme theme : themes) {
                sum += theme.chanceOfAppearing;
            }
            int roll = new Random().nextInt(sum);
            int partial = 0;
            for (Theme theme : themes) {
                partial += theme.chanceOfAppearing;
                if (roll <= partial) return themedModels.get(theme);
            }
        } else {
            Array<Theme> themes = new Array<>();
            EnumMap<WallType, Model> themedModels;
            // @todo create a default theme to return and create some different ones to save. And backup. And use them.
            Json json = new Json();
        }
        return null;
    }

/*
    private static EnumMap<WallType, Model> createThemedWallModelsModels(Theme theme) {
        return createWalls(theme);
    }

    private static EnumMap<WallType, Model> createWalls(Theme theme) {
        EnumMap<WallType, Model> models = new EnumMap<>(WallType.class);
        ModelBuilder builder = new ModelBuilder();
        builder.begin();
        builder.part("floor", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position,
                new Material(ColorAttribute.createDiffuse(theme.color))).rect(0, 0, 0, SIZE, 0, 0, SIZE, 0, SIZE, 0, 0, SIZE, 0, -1, 0);
        builder.part("wall", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(theme.color))).rect(0, 0, 0, SIZE, 4, 0, SIZE, 0, SIZE, 0, 3, SIZE, 0, -1, 0);
        models.put(WallType.ONE_SIDE, builder.end());
        return models;
        //@todo use this class for getting models to Dungeon, likea you have to.
    }*/

    /*
    public static class DungeonSection implements Json.Serializable, Cacheable {

        private static final int SCALE = 10;
        public static final float SIZE = 1f * SCALE; //square
        public static final float HEIGHT = 1.5f * SCALE;
        private static ModelBuilder builder = new ModelBuilder();
        private static Model floorModel = builder.createRect(0, 0, 0, SIZE, 0, 0, SIZE, 0, SIZE, 0, 0, SIZE, 0, -1, 0,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        private static Model wallModel = builder.createRect(0, 0, 0, SIZE, 0, 0, SIZE, HEIGHT, 0, 0, HEIGHT, 0, 0, 0, 1,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal); //// TODO: 2016-09-01 change to meshpartbuilder
        public Vector2 point; //used by json thing
        public ArrayList<Object> occupyingObject;
        private ArrayList<Vector2> next; //used by json thing
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
        public void cacheModel(ModelCache cache, Environment environment) {
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
     */

    public enum WallType {

        TWO_SIDES(), CORNER(), ONE_SIDE(), NO_SIDES;

        WallType(Vector2... emptySpaces) {
            this.emptySpace = emptySpace;
        }

        Vector2[] emptySpace;
    }

    public static class Theme {

        public final static int NEVER = 0;
        public final static int START = 0;

        Theme(String name, Color color, int chanceOfAppearing, int startLevel, int endLevel) {
            this.name = name;
            this.color = color;
            this.chanceOfAppearing = chanceOfAppearing;
            this.startLevel = startLevel;
            this.endLevel = endLevel;
        }

        String name;
        Color color;
        int chanceOfAppearing;
        int startLevel;
        int endLevel;
        }
}
