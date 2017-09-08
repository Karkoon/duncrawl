package ashlified.systems;

import ashlified.components.DirectionComponent;
import ashlified.components.PositionComponent;
import ashlified.components.SpriterModelComponent;
import ashlified.dungeon.DungeonSection;
import ashlified.util.CardinalDirection;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public class InputSystem extends EntitySystem {

    private Family family = Family
            .all(PositionComponent.class, DirectionComponent.class)
            .exclude(SpriterModelComponent.class)
            .get();
    private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DirectionComponent> directionMapper = ComponentMapper.getFor(DirectionComponent.class);
    private Entity controlledEntity;
    private Camera camera;

    public InputSystem(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        if (entities.size() != 1) {
            Gdx.app.error("InputSystem", "No controllable entity or more than one entity");
            Gdx.app.exit();
        }
        controlledEntity = entities.first();
        Gdx.input.setInputProcessor(new InputController());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector3 pos = posMapper.get(controlledEntity).getPosition();
        Vector3 lookingDirection = directionMapper.get(controlledEntity).getDirection().value;
        camera.position.interpolate(pos, 0.5f, Interpolation.linear);
        camera.direction.interpolate(lookingDirection, 0.5f, Interpolation.linear);
        camera.update();
    }

    private class InputController extends InputAdapter {

        @Override
        public boolean keyDown(int keycode) {
            CardinalDirection movingDirection = directionMapper.get(controlledEntity).getDirection();
            PositionComponent posComp = posMapper.get(controlledEntity);
            DungeonSection currentSection = posComp.getOccupiedSection();
            Vector3 pos = posComp.getPosition();
            switch (keycode) {
                case Input.Keys.W:
                    DungeonSection adjacentSection = currentSection.getAdjacentSection(movingDirection);
                    posComp.setOccupiedSection(adjacentSection);
                    pos.set(adjacentSection.getPosition().x, 6.5f, adjacentSection.getPosition().z);
                    break;
                case Input.Keys.S:
                    DungeonSection oppositeAdjacentSection = currentSection.getAdjacentSection(CardinalDirection.oppositeOf(movingDirection));
                    posComp.setOccupiedSection(oppositeAdjacentSection);
                    pos.set(oppositeAdjacentSection.getPosition().x, 6.5f, oppositeAdjacentSection.getPosition().z);
                    break;
                case Input.Keys.A:
                    directionMapper.get(controlledEntity).setDirection(CardinalDirection.leftOf(movingDirection));
                    break;
                case Input.Keys.D:
                    directionMapper.get(controlledEntity).setDirection(CardinalDirection.rightOf(movingDirection));
                    break;
                case Input.Keys.SPACE:
                    Gdx.app.exit();
                    break;
                default:
                    break;
            }
            return super.keyDown(keycode);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {

            return super.touchDragged(screenX, screenY, pointer);
        }
    }
}
