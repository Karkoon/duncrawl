package ashlified.dungeon;

import ashlified.Assets;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 26.04.17.
 */
public class DungeonSectionModel {

    private final static float SIZE = 10f;
    private final static float HEIGHT = 15f;
    private ModelInstance modelInstance;
    private DungeonSection section;
    private float rotation = 0;
    private Vector2 correction;

    public DungeonSectionModel(DungeonSection section, WallModelsAccessor models) {
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
        return HEIGHT;
    }

    public static float getSize() {
        return SIZE;
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
            correction.y = -SIZE;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && !hasWestBorder) {
            correction.x = -SIZE;
            rotation = -90;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            correction.y = -SIZE;
            correction.x = -SIZE;
            rotation = 180;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (hasNorthBorder && !hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            correction.y = 0;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!hasNorthBorder && !hasSouthBorder && hasEastBorder && hasWestBorder) {
            rotation = 90;
            correction.y = -SIZE;
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (hasNorthBorder && hasSouthBorder && !hasEastBorder && !hasWestBorder) {
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (!hasNorthBorder && hasSouthBorder && hasWestBorder && !hasEastBorder) {
            correction.y = -SIZE;
            rotation = 90;
            return WallModelsAccessor.WallType.CORNER;
        } else if (!hasNorthBorder && hasSouthBorder && !hasWestBorder && hasEastBorder) {
            correction.x = -SIZE;
            correction.y = -SIZE;
            rotation = 180;
            return WallModelsAccessor.WallType.CORNER;
        } else if (hasNorthBorder && !hasSouthBorder && hasWestBorder && !hasEastBorder) {
            return WallModelsAccessor.WallType.CORNER;
        } else if (hasNorthBorder && !hasSouthBorder && !hasWestBorder && hasEastBorder) {
            correction.x = -SIZE;
            rotation = 270;
            return WallModelsAccessor.WallType.CORNER;
        }
        return WallModelsAccessor.WallType.NO_SIDES;
    }
}