package ashlified.graphics;

import ashlified.dungeon.DungeonSection;
import ashlified.util.CardinalDirection;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by karkoon on 26.04.17.
 * Decides which type of model a dungeon section uses, rotates it and creates a model instance.
 */
public class DungeonSectionRepresentation {

    private ModelInstance modelInstance;
    private DungeonSection section;
    private float rotation = 0;

    DungeonSectionRepresentation(DungeonSection section, WallModelsAccessor modelsAccessor) {
        this.section = section;
        modelInstance = new ModelInstance(modelsAccessor.get(determineSectionType()));
        modelInstance.transform.translate(section.getPosition());
        modelInstance.transform.rotate(Vector3.Y, rotation);
    }

    private WallModelsAccessor.WallType determineSectionType() {
        boolean northBorder = true;
        boolean southBorder = true;
        boolean westBorder = true;
        boolean eastBorder = true;

        if (section.getConnections().containsKey(CardinalDirection.WEST)
                && section.getConnection(CardinalDirection.WEST).getToNode() != section) {
            westBorder = false;
        }

        if (section.getConnections().containsKey(CardinalDirection.EAST)
                && section.getConnection(CardinalDirection.EAST).getToNode() != section) {
            eastBorder = false;
        }

        if (section.getConnections().containsKey(CardinalDirection.NORTH)
                && section.getConnection(CardinalDirection.NORTH).getToNode() != section) {
            northBorder = false;
        }

        if (section.getConnections().containsKey(CardinalDirection.SOUTH)
                && section.getConnection(CardinalDirection.SOUTH).getToNode() != section) {
            southBorder = false;
        }

        // sorry
        if (!northBorder && !southBorder && !eastBorder && westBorder) {
            rotation = 90;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && !westBorder) {
            rotation = -90;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!northBorder && southBorder && !eastBorder && !westBorder) {
            rotation = 180;
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (northBorder && !southBorder && !eastBorder && !westBorder) {
            return WallModelsAccessor.WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && westBorder) {
            rotation = 90;
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (northBorder && southBorder && !eastBorder && !westBorder) {
            return WallModelsAccessor.WallType.TWO_SIDES;
        } else if (!northBorder && southBorder && westBorder && !eastBorder) {
            rotation = 90;
            return WallModelsAccessor.WallType.CORNER;
        } else if (!northBorder && southBorder && !westBorder && eastBorder) {
            rotation = 180;
            return WallModelsAccessor.WallType.CORNER;
        } else if (northBorder && !southBorder && westBorder && !eastBorder) {
            return WallModelsAccessor.WallType.CORNER;
        } else if (northBorder && !southBorder && !westBorder && eastBorder) {
            rotation = 270;
            return WallModelsAccessor.WallType.CORNER;
        }
        return WallModelsAccessor.WallType.NO_SIDES;
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }
}