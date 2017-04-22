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
            if (section.getPoint().equals(position)) return section;
        }
        return null;
    }

    public DungeonSection getRandomDungeonSection() {
        return grid.get(new Random().nextInt(grid.size()));
    }

    public DungeonSection getSpawnDungeonSection() {
        return grid.get(0);
    }

}
