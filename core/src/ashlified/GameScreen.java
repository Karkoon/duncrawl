package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.HttpDungeonProvider;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PointLightComponent;
import ashlified.entitycomponentsystem.entityinitializers.GameEntities;
import ashlified.entitycomponentsystem.entitylisteners.LightComponentListener;
import ashlified.entitycomponentsystem.entitysystems.EntitySystems;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.graphics.Graphics;
import ashlified.inputprocessors.KeyboardInput;
import ashlified.inputprocessors.TouchInput;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by @Karkoon on 2016-08-30.
 * Manages the main game.
 */
public class GameScreen extends ScreenAdapter {

    private Graphics graphics;
    private PooledEngine engine;
    private AssetManager assetManager;
    private TurnEndSignal endTurnSignal = new TurnEndSignal();

    public GameScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        engine = new PooledEngine();
        Dungeon dungeon = new HttpDungeonProvider().getNewDungeon(MathUtils.random(Integer.MAX_VALUE - 1), 50, 20);
        graphics = new Graphics(dungeon, assetManager);
        addEntityListeners();
        GameEntities gameEntities = new GameEntities(engine, dungeon, assetManager);
        gameEntities.createInitialEntities();
        setInputProcessors();
        EntitySystems entitySystems = new EntitySystems(assetManager, graphics, dungeon);
        entitySystems.addSystemsTo(engine);
        endTurnSignal.add(entitySystems.getNpcAiSystem());
    }

    private void setInputProcessors() {
        Entity player = getControllableEntityFrom(engine);
        Gdx.input.setInputProcessor(new InputMultiplexer(new KeyboardInput(endTurnSignal, player), new TouchInput(player)));
    }

    private Entity getControllableEntityFrom(Engine engine) {
        Family family = Family
                .all(PlayerComponent.class)
                .get();
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        if (entities.size() != 1) {
            Gdx.app.error("InputSystem", "No controllable entity or more than one entity");
            Gdx.app.exit();
        }
        return entities.first();
    }

    private void addEntityListeners() {
        engine.addEntityListener(Family.all(PointLightComponent.class).get(),
                new LightComponentListener(graphics.getModelInstanceRenderer().getEnvironment()));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        graphics.begin();
        graphics.render();
        graphics.end();
    }

    @Override
    public void resize(int width, int height) {
        graphics.resize(width, height);
    }
}
