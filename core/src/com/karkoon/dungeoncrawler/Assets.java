package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.EnumMap;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;
import static com.karkoon.dungeoncrawler.Dungeon.DungeonSection.SIZE;

/**
 * Created by Pc on 2016-09-10.
 * Used with asset manager to give easy access to files.
 * Contains game-generated models.
 */
public class Assets {

    public EnumMap<WallType, Model> createThemedWallModelsModels(Theme theme) {
        return createWalls(theme);
    }

    private EnumMap<WallType, Model> createWalls(Theme theme) {
        EnumMap<WallType, Model> models = new EnumMap<>(WallType.class);
        ModelBuilder builder = new ModelBuilder();
        builder.begin();
        builder.part("floor", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(theme.color))).rect(0, 0, 0, SIZE, 0, 0, SIZE, 0, SIZE, 0, 0, SIZE, 0, -1, 0);
        builder.part("wall", GL20.GL_TRIANGLES, Usage.Normal | Usage.Position, new Material(ColorAttribute.createDiffuse(theme.color))).rect(0, 0, 0, SIZE, 4, 0, SIZE, 0, SIZE, 0, 3, SIZE, 0, -1, 0);
        models.put(WallType.ONE_SIDE, builder.end());
        return models;
        //@todo use this class for getting models to Dungeon, likea you have to.
    }

    private enum WallType {
        TWO_SIDES, CORNER, ONE_SIDE, NO_SIDES
    }

    class Theme {

        Color color;

        Theme(Color color) {
            this.color = color;
        }
    }
}
