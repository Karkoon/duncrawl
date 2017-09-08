package ashlified.dungeon;

import ashlified.graphics.WallModelsAccessor;
import ashlified.util.CardinalDirection;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 26.04.17.
 */
public class DungeonSectionRepresentation {

    private final static float SIZE = 10f;
    private final static float HEIGHT = 15f;

    private ModelInstance modelInstance;
    private DungeonSection section;
    private float rotation = 0;

    public DungeonSectionRepresentation(DungeonSection section, WallModelsAccessor modelsAccessor) {
        this.section = section;
        modelInstance = new ModelInstance(modelsAccessor.get(determineSectionType()));
        modelInstance.transform.translate(section.getPosition());
        modelInstance.transform.rotate(Vector3.Y, rotation);

    }

    public static float getHeight() {
        return HEIGHT;
    }

    public static float getSize() {
        return SIZE;
    }

    private WallType determineSectionType() {
        boolean northBorder = true;
        boolean southBorder = true;
        boolean westBorder = true;
        boolean eastBorder = true;

        if (section.getAdjacentSection(CardinalDirection.WEST) != section) {
            westBorder = false;
        }

        if (section.getAdjacentSection(CardinalDirection.EAST) != section) {
            eastBorder = false;
        }

        if (section.getAdjacentSection(CardinalDirection.NORTH) != section) {
            northBorder = false;
        }

        if (section.getAdjacentSection(CardinalDirection.SOUTH) != section) {
            southBorder = false;
        }

        // sorry
        if (!northBorder && !southBorder && !eastBorder && westBorder) {
            rotation = 90;
            return WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && !westBorder) {
            rotation = -90;
            return WallType.ONE_SIDE;
        } else if (!northBorder && southBorder && !eastBorder && !westBorder) {
            rotation = 180;
            return WallType.ONE_SIDE;
        } else if (northBorder && !southBorder && !eastBorder && !westBorder) {
            return WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && westBorder) {
            rotation = 90;
            return WallType.TWO_SIDES;
        } else if (northBorder && southBorder && !eastBorder && !westBorder) {
            return WallType.TWO_SIDES;
        } else if (!northBorder && southBorder && westBorder && !eastBorder) {
            rotation = 90;
            return WallType.CORNER;
        } else if (!northBorder && southBorder && !westBorder && eastBorder) {
            rotation = 180;
            return WallType.CORNER;
        } else if (northBorder && !southBorder && westBorder && !eastBorder) {
            return WallType.CORNER;
        } else if (northBorder && !southBorder && !westBorder && eastBorder) {
            rotation = 270;
            return WallType.CORNER;
        }
        return WallType.NO_SIDES;
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }
}