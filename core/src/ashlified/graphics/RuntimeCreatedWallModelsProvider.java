package ashlified.graphics;

import ashlified.AssetPaths;
import ashlified.dungeon.DungeonSectionRepresentation;
import ashlified.dungeon.LevelTheme;
import ashlified.dungeon.WallType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.EnumMap;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;

/**
 * Created by kacper on 25.01.17.
 */
public class RuntimeCreatedWallModelsProvider implements WallModelsProvider {

    private float size = DungeonSectionRepresentation.getSize(); //it's a square
    private float halfSize = size / 2f;
    private float height = DungeonSectionRepresentation.getHeight();
    private AssetManager manager;

    RuntimeCreatedWallModelsProvider(AssetManager manager) {
        this.manager = manager;
    }

    private EnumMap<WallType, Model> createWallModels(LevelTheme theme) {
        long attributes = Usage.Normal | Usage.Position | Usage.TextureCoordinates;

        Texture wallTexture = manager.get(AssetPaths.WALL_TEXTURE);
        TextureRegion region = new TextureRegion(wallTexture);

        Material wall = new Material(ColorAttribute.createDiffuse(theme.getColor()), TextureAttribute.createDiffuse(region), new BlendingAttribute(false, 1));
        Material floor = new Material(ColorAttribute.createDiffuse(theme.getColor()), TextureAttribute.createDiffuse(region), new BlendingAttribute(false, 1));
        Material ceiling = new Material(ColorAttribute.createDiffuse(theme.getColor()), TextureAttribute.createDiffuse(region), new BlendingAttribute(false, 1));

        ModelBuilder builder = new ModelBuilder();
        EnumMap<WallType, Model> models = new EnumMap<>(WallType.class);
        models.put(WallType.ONE_SIDE, createOneSide(builder, attributes, floor, ceiling, wall));
        models.put(WallType.CORNER, createCorner(builder, attributes, floor, ceiling, wall));
        models.put(WallType.TWO_SIDES, createTwoSides(builder, attributes, floor, ceiling, wall));
        models.put(WallType.NO_SIDES, createNoSides(builder, attributes, floor, ceiling));

        return models;
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
                .rect(-halfSize, 0, -halfSize,
                        -halfSize, 0, halfSize,
                        halfSize, 0, halfSize,
                        halfSize, 0, -halfSize,
                        0, 1f, 0);
    }

    private void createCeiling(ModelBuilder builder, long attributes, Material ceiling) {
        builder.part("ceiling", GL20.GL_TRIANGLES, attributes, ceiling)
                .rect(-halfSize, height, -halfSize,
                        halfSize, height, -halfSize,
                        halfSize, height, halfSize,
                        -halfSize, height, halfSize,
                        0, -1, 0);
    }

    private Model createTwoSides(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(halfSize, 0, halfSize,
                        -halfSize, 0, halfSize,
                        -halfSize, height, halfSize,
                        halfSize, height, halfSize,
                        0, 1, -7);
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
                .rect(-halfSize, 0, -halfSize,
                        halfSize, 0, -halfSize,
                        halfSize, height, -halfSize,
                        -halfSize, height, -halfSize,
                        0, 1, 7);
    }

    private Model createCorner(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(-halfSize, 0, halfSize,
                        -halfSize, 0, -halfSize,
                        -halfSize, height, -halfSize,
                        -halfSize, height, halfSize,
                        7, 1, 0);
        return builder.end();
    }

    @Override
    public EnumMap<WallType, Model> getNewModels(LevelTheme theme) {
        Gdx.app.log("LevelTheme", theme.getName());
        return createWallModels(theme);
    }

}
