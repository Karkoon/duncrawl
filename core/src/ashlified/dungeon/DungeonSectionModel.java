package ashlified.dungeon;

import ashlified.Assets;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 26.04.17.
 */
public class DungeonSectionModel {

    private final static float size = 10f;
    private final static float height = 15f;
    private static WallModelsAccessor models = Assets.getWallModelsAccessor();
    private ModelInstance modelInstance;
    private DungeonSection section;
    private float rotation = 0;
    private Vector2 correction;

    public DungeonSectionModel(DungeonSection section) {
        this.section = section;
        correction = new Vector2(0, 0);
        modelInstance = new ModelInstance(models.get(determineSectionType()));
        modelInstance.transform.translate(section.getPoint().x - 5 - correction.x,
                0,
                section.getPoint().y - 5 - correction.y);
        modelInstance.transform.rotate(Vector3.Y, rotation);
        rotation = 0;
    }

    public static float getHeight() {
        return height;
    }

    public static float getSize() {
        return size;
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    private WallModelsAccessor.WallType determineSectionType() { //sorry
        boolean hasNorthBorder = true;
        boolean hasSouthBorder = true;
        boolean hasWestBorder = true;
        boolean hasEastBorder = true;

        for (Vector2 nextSection : section.getNext()) {
            if (nextSection.x < section.getPoint().x) {
                hasWestBorder = false;
            } else if (nextSection.x > section.getPoint().x) {
                hasEastBorder = false;
            } else if (nextSection.y < section.getPoint().y) {
                hasNorthBorder = false;
            } else if (nextSection.y > section.getPoint().y) {
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