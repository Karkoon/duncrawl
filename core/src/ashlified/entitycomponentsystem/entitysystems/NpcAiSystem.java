package ashlified.entitycomponentsystem.entitysystems;

import ashlified.dungeon.DungeonSection;
import ashlified.entitycomponentsystem.components.*;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Interpolation;

public class NpcAiSystem extends IteratingSystem implements Listener<EntitySystem> {

    private final static float INTERPOLATION_ALPHA = 0.2f;

    private static ComponentMapper<ViewDistanceComponent> viewDistanceMapper = ComponentMapper.getFor(ViewDistanceComponent.class);
    private static ComponentMapper<TargetComponent> targetMapper = ComponentMapper.getFor(TargetComponent.class);
    private static ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private static ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    private Entity currentEntity;
    private ViewDistanceComponent viewDistance;
    private TargetComponent target;
    private PositionComponent posComp;

    private boolean endOfPlayersTurn = false;
    private GraphPath<DungeonSection> path;
    private IndexedAStarPathFinder<DungeonSection> p;
    private Heuristic<DungeonSection> heuristic = new Heuristic<DungeonSection>() {
        @Override
        public float estimate(DungeonSection node, DungeonSection endNode) {
            return node.getPosition().dst(endNode.getPosition());
        }
    };

    NpcAiSystem(IndexedGraph<DungeonSection> dungeon) {
        super(Family.all(ViewDistanceComponent.class, TargetComponent.class, PositionComponent.class).get());
        path = new DefaultGraphPath<>();
        p = new IndexedAStarPathFinder<>(dungeon);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.currentEntity = entity;
        posComp = posMapper.get(currentEntity);
        DungeonSection currentSection = posComp.getOccupiedSection();
        if (endOfPlayersTurn) {
            initializeUsedComponents();
            if (isAbleToDoAnyAction() && seesTarget()) {
                if (nextToTarget()) {
                    Gdx.app.log("END OF TURN", "NPC PROCESSING");
                    AttackComponent attack = getEngine().createComponent(AttackComponent.class);
                    attack.setEnemy(target.getTarget().getOccupyingEntities().get(0));
                    entity.add(attack);
                } else {
                    findPathToTarget();
                    if (!pathObstructed()) {
                        moveTowardsTarget();
                    }
                }
            }
        }
        posComp.getPosition().interpolate(currentSection.getPosition(), INTERPOLATION_ALPHA, Interpolation.linear);
    }

    private boolean pathObstructed() {
        Gdx.app.log("pab", Integer.toString(path.getCount()));
        return path.get(1).getOccupyingEntities().size() > 0;
    }

    private void moveTowardsTarget() {
        posComp.getOccupiedSection().getOccupyingEntities().remove(currentEntity);
        posComp.setOccupiedSection(path.get(1));
        posComp.getOccupiedSection().addOccupyingObject(currentEntity);
    }

    private void findPathToTarget() {
        DungeonSection occupiedSection = posComp.getOccupiedSection();
        p.searchNodePath(occupiedSection,
                target.getTarget(), heuristic,
                path);
    }

    private boolean nextToTarget() {
        boolean isTargetAtNextDungeonSection = false;
        for (CardinalDirection direction : CardinalDirection.values()) {
            Connection potentialConnection = posComp.getOccupiedSection().getConnection(direction);
            if (potentialConnection != null) {
                DungeonSection adjacentSection = (DungeonSection) potentialConnection.getToNode();
                isTargetAtNextDungeonSection = adjacentSection.getPosition().idt(target.getTarget().getPosition());
                Gdx.app.log("Position cam", target.getTarget().getPosition().toString());
                Gdx.app.log("Position section", adjacentSection.getPosition().toString());
                Gdx.app.log("POSITION", Boolean.toString(isTargetAtNextDungeonSection));
            }
            if (isTargetAtNextDungeonSection) return true;
        }
        return false;
    }

    private boolean isAbleToDoAnyAction() {
        return healthMapper.get(currentEntity).getHealth() > 0;
    }
    private boolean seesTarget() {
        boolean isCloseEnough = viewDistance.getViewDistance() > target.getTarget().getPosition().dst(posComp.getPosition());
        return true && isCloseEnough; // TODO: 28.08.17 implement line of sight in addition to distance check
    }

    private void initializeUsedComponents() {
        viewDistance = viewDistanceMapper.get(currentEntity);
        target = targetMapper.get(currentEntity);
        posComp = posMapper.get(currentEntity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        endOfPlayersTurn = false;
    }

    @Override
    public void receive(Signal<EntitySystem> signal, EntitySystem object) {
        endOfPlayersTurn = true;
    }

}
