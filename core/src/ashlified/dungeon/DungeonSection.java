package ashlified.dungeon;

import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by karkoon on 01.02.17.
 * Used in pathfinding a node. Contains connections.
 * It's positions and positions of its neighbours are read to create an accurate model instance.
 * Contains a list of objects that are currently occupying it to block other NPCs from moving onto it.
 */

public class DungeonSection {

    private ArrayList<Entity> occupyingEntities = new ArrayList<>();
    private ArrayList<Vector3> adjacentPositions = new ArrayList<>();
    private Array<Connection<DungeonSection>> adjacentSections = new Array<>();
    private Vector3 position;

    private EnumMap<CardinalDirection, DefaultConnection<DungeonSection>> adjacentSectionsMap = new EnumMap<>(CardinalDirection.class);

    ArrayList<Vector3> getAdjacentSectionPositions() {
        return adjacentPositions;
    }

    public EnumMap<CardinalDirection, DefaultConnection<DungeonSection>> getConnectionsMap() {
        return adjacentSectionsMap;
    }

    public Array<Connection<DungeonSection>> getConnections() {
        return adjacentSections;
    }

    public DefaultConnection<DungeonSection> getConnection(CardinalDirection direction) {
        return adjacentSectionsMap.get(direction);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public ArrayList<Entity> getOccupyingEntities() {
        return occupyingEntities;
    }

    public void addOccupyingObject(Entity entity) {
        occupyingEntities.add(entity);
    }
}