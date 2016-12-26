package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.EnumMap;

/**
 * Created by kacper on 25.12.16.
 */
public class WallModels {

    private static final int SCALE = 10;
    public static final float SIZE = 1f * SCALE; //square
    public static final float HEIGHT = 1.5f * SCALE;

    private EnumMap<WallType, Model> models = new EnumMap<>(WallType.class);

    WallModels(Theme theme) {
        createWallModels(theme);
    }

    public Model get(WallType key) {
        return models.get(key);
    }

    private void createWallModels(Theme theme) {
        ModelBuilder builder = new ModelBuilder();
        long attributes = VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position;
        Material wall = new Material(ColorAttribute.createDiffuse(theme.color));
        Material floor = new Material(ColorAttribute.createDiffuse(theme.color));
        Material ceiling = new Material(ColorAttribute.createDiffuse(theme.color.mul(1.5f)));

        models.put(WallType.ONE_SIDE, createOneSide(builder, attributes, floor, ceiling, wall));
        models.put(WallType.CORNER, createCorner(builder, attributes, floor, ceiling, wall));
        models.put(WallType.TWO_SIDES, createTwoSides(builder, attributes, floor, ceiling, wall));
        models.put(WallType.NO_SIDES, createNoSides(builder, attributes, floor, ceiling));
    }

    private Model createOneSide(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        return builder.end();
    }

    private void createFloor(ModelBuilder builder, long attributes, Material floor) {
        builder.part("floor", GL20.GL_TRIANGLES, attributes, floor)
                .rect(0, 0, 0,
                        0, 0, SIZE,
                        SIZE, 0, SIZE,
                        SIZE, 0, 0,
                        0, 1, 0);
    }

    private void createCeiling(ModelBuilder builder, long attributes, Material ceiling) {
        builder.part("ceiling", GL20.GL_TRIANGLES, attributes, ceiling)
                .rect(0, HEIGHT, 0,
                        SIZE, HEIGHT, 0,
                        SIZE, HEIGHT, SIZE,
                        0, HEIGHT, SIZE,
                        0, -1, 0);
    }

    private Model createTwoSides(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(SIZE, 0, SIZE,
                        0, 0, SIZE,
                        0, HEIGHT, SIZE,
                        SIZE, HEIGHT, SIZE,
                        -1, 0, 0);
        return builder.end();
    }

    private Model createNoSides(ModelBuilder builder, long attributes, Material floor, Material ceiling) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        return builder.end();
    }

    private void createWall(ModelBuilder builder, long attributes, Material wall) {
        builder.part("wall", GL20.GL_TRIANGLES, attributes, wall)
                .rect(0, 0, 0,
                        SIZE, 0, 0,
                        SIZE, HEIGHT, 0,
                        0, HEIGHT, 0,
                        1, 0, 0);
    }

    private Model createCorner(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(0, 0, SIZE,
                        0, 0, 0,
                        0, HEIGHT, 0,
                        0, HEIGHT, SIZE,
                        0, 0, -1);
        return builder.end();
    }

    public enum WallType {
        TWO_SIDES, CORNER, ONE_SIDE, NO_SIDES
    }

    /**
     * Class for deserialization.
     */
    public static class Theme {

        public final static int NEVER = 0;
        public final static int START = 0;

        String name;
        Color color;
        int chanceOfAppearing;
        int startLevel;
        int endLevel;
    }
}