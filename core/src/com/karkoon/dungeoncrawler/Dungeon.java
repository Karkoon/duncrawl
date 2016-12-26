package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
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
    public void cacheModel(ModelCache cache) {
        for (DungeonSection section : grid) {
            section.cacheModel(cache);
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

        private static WallModels models = Assets.getWallModels();
        public Vector2 point; //used by json thing
        public Vector2 correction;
        public ArrayList<Object> occupyingObject;
        float rotation = 0;
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
            point = new Vector2(point.x * 10, point.y * 10);
            for (Vector2 vector2 : next) {
                vector2.set(vector2.x * 10, vector2.y * 10);
            }

            occupyingObject = new ArrayList<>();
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

        private WallModels.WallType determineSectionType() {
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
                correction.y = -10;
                return WallModels.WallType.ONE_SIDE;
            } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && !hasWestBorder) {
                correction.x = -10;
                rotation = -90;
                return WallModels.WallType.ONE_SIDE;
            } else if (!hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
                correction.y = -10;
                correction.x = -10;
                rotation = 180;
                return WallModels.WallType.ONE_SIDE;
            } else if (hasNorthBorder && !hasSouthBorder && !hasEastBorder && !hasWestBorder) {
                correction.y = 0;
                return WallModels.WallType.ONE_SIDE;
            } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && hasWestBorder) {
                rotation = 90;
                correction.y = -10;
                return WallModels.WallType.TWO_SIDES;
            } else if (hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
                return WallModels.WallType.TWO_SIDES;
            } else if (!hasNorthBorder && hasSouthBorder && hasWestBorder && !hasEastBorder) {
                correction.y = -10;
                rotation = 90;
                return WallModels.WallType.CORNER;
            } else if (!hasNorthBorder && hasSouthBorder && !hasWestBorder && hasEastBorder) {
                correction.x = -10;
                correction.y = -10;
                rotation = 180;
                return WallModels.WallType.CORNER;
            } else if (hasNorthBorder && !hasSouthBorder && hasWestBorder && !hasEastBorder) {
                return WallModels.WallType.CORNER;
            } else if (hasNorthBorder && !hasSouthBorder && !hasWestBorder && hasEastBorder) {
                correction.x = -10;
                rotation = 270;
                return WallModels.WallType.CORNER;
            }
            return WallModels.WallType.NO_SIDES;
        }
    }
}
