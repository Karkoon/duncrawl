package ashlified.dungeon;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Describes the connections between dungeon sections.
 */
public class DungeonConnection implements Connection<DungeonSection> {

    private DungeonSection fromNode;
    private DungeonSection toNode;
    private int cost = 1;

    public DungeonConnection(DungeonSection section, DungeonSection adjacent) {
        fromNode = section;
        toNode = adjacent;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public DungeonSection getFromNode() {
        return fromNode;
    }

    @Override
    public DungeonSection getToNode() {
        return toNode;
    }
}
