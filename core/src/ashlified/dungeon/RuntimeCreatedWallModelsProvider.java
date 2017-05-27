package ashlified.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
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
    private float height = DungeonSectionRepresentation.getHeight();

    private EnumMap<WallType, Model> createWallModels(WallTheme theme) {
        long attributes = Usage.Normal | Usage.Position | Usage.TextureCoordinates;

        Texture texture = new Texture(Gdx.files.internal("178.JPG"));
        TextureRegion region = new TextureRegion(texture);

        Material wall = new Material(ColorAttribute.createDiffuse(theme.getColor()), TextureAttribute.createDiffuse(region));
        Material floor = new Material(ColorAttribute.createDiffuse(theme.getColor()), TextureAttribute.createDiffuse(region));
        Material ceiling = new Material(ColorAttribute.createDiffuse(theme.getColor().mul(1.2f)));

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
                .rect(0, 0, 0,
                        0, 0, size,
                        size, 0, size,
                        size, 0, 0,
                        0, 1, 0);
    }

    private void createCeiling(ModelBuilder builder, long attributes, Material ceiling) {
        builder.part("ceiling", GL20.GL_TRIANGLES, attributes, ceiling)
                .rect(0, height, 0,
                        size, height, 0,
                        size, height, size,
                        0, height, size,
                        0, -1, 0);
    }

    private Model createTwoSides(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(size, 0, size,
                        0, 0, size,
                        0, height, size,
                        size, height, size,
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
                        size, 0, 0,
                        size, height, 0,
                        0, height, 0,
                        1, 0, 0);
    }

    private Model createCorner(ModelBuilder builder, long attributes, Material floor, Material ceiling, Material wall) {
        builder.begin();
        createFloor(builder, attributes, floor);
        createCeiling(builder, attributes, ceiling);
        createWall(builder, attributes, wall);
        builder.part("wall2", GL20.GL_TRIANGLES, attributes, wall)
                .rect(0, 0, size,
                        0, 0, 0,
                        0, height, 0,
                        0, height, size,
                        0, 0, -1);
        return builder.end();
    }

    @Override
    public EnumMap<WallType, Model> getNewModels(WallTheme theme) {
        return createWallModels(theme);
    }

}
