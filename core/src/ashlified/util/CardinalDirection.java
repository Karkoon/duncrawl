package ashlified.util;

/**
 * Defines the basic directions.
 * Provides some utility methods.
 */
public enum CardinalDirection {

    NORTH(new com.badlogic.gdx.math.Vector3(0, 0, -1)),
    SOUTH(new com.badlogic.gdx.math.Vector3(0, 0, 1)),
    WEST(new com.badlogic.gdx.math.Vector3(-1, 0, 0)),
    EAST(new com.badlogic.gdx.math.Vector3(1, 0, 0));

    public final com.badlogic.gdx.math.Vector3 value;

    CardinalDirection(com.badlogic.gdx.math.Vector3 direction) {
        value = direction;
    }

    public static CardinalDirection oppositeOf(CardinalDirection direction) {
        switch (direction) {
            case EAST:
                return WEST;
            case NORTH:
                return SOUTH;
            case WEST:
                return EAST;
            case SOUTH:
                return NORTH;
            default:
                return null;
        }
    }

    public static CardinalDirection leftOf(CardinalDirection direction) {
        switch (direction) {
            case EAST:
                return NORTH;
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            default:
                return null;
        }
    }

    public static CardinalDirection rightOf(CardinalDirection direction) {
        switch (direction) {
            case WEST:
                return NORTH;
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            default:
                return null;
        }
    }
}
