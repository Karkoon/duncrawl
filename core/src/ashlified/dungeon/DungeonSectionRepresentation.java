package ashlified.dungeon;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
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
    private Vector2 correction;

    public DungeonSectionRepresentation(DungeonSection section, WallModelsAccessor models) {
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

    private WallType determineSectionType() {
        boolean northBorder = true;
        boolean southBorder = true;
        boolean westBorder = true;
        boolean eastBorder = true;

        for (Vector2 nextSection : section.getNext()) {
            if (nextSection.x < section.getPoint().x) {
                westBorder = false;
            } else if (nextSection.x > section.getPoint().x) {
                eastBorder = false;
            } else if (nextSection.y < section.getPoint().y) {
                northBorder = false;
            } else if (nextSection.y > section.getPoint().y) {
                southBorder = false;
            }
        }

        // sorry
        if (!northBorder && !southBorder && !eastBorder && westBorder) {
            rotation = 90;
            correction.y = -SIZE;
            return WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && !westBorder) {
            correction.x = -SIZE;
            rotation = -90;
            return WallType.ONE_SIDE;
        } else if (!northBorder && southBorder && !eastBorder && !westBorder) {
            correction.y = -SIZE;
            correction.x = -SIZE;
            rotation = 180;
            return WallType.ONE_SIDE;
        } else if (northBorder && !southBorder && !eastBorder && !westBorder) {
            correction.y = 0;
            return WallType.ONE_SIDE;
        } else if (!northBorder && !southBorder && eastBorder && westBorder) {
            rotation = 90;
            correction.y = -SIZE;
            return WallType.TWO_SIDES;
        } else if (northBorder && southBorder && !eastBorder && !westBorder) {
            return WallType.TWO_SIDES;
        } else if (!northBorder && southBorder && westBorder && !eastBorder) {
            correction.y = -SIZE;
            rotation = 90;
            return WallType.CORNER;
        } else if (!northBorder && southBorder && !westBorder && eastBorder) {
            correction.x = -SIZE;
            correction.y = -SIZE;
            rotation = 180;
            return WallType.CORNER;
        } else if (northBorder && !southBorder && westBorder && !eastBorder) {
            return WallType.CORNER;
        } else if (northBorder && !southBorder && !westBorder && eastBorder) {
            correction.x = -SIZE;
            rotation = 270;
            return WallType.CORNER;
        }
        return WallType.NO_SIDES;
    }
}