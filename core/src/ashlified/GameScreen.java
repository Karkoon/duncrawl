package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.LocalDungeonProvider;
import ashlified.entitycomponentsystem.components.PlayerComponent;
import ashlified.entitycomponentsystem.components.PointLightComponent;
import ashlified.entitycomponentsystem.entityinitializers.GameEntities;
import ashlified.entitycomponentsystem.entitylisteners.LightComponentListener;
import ashlified.entitycomponentsystem.entitysystems.EntitySystems;
import ashlified.entitycomponentsystem.signals.PlayerTurnEndSignal;
import ashlified.graphics.Graphics;
import ashlified.gui.UserInterfaceStage;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by @Karkoon on 2016-08-30.
 * Manages the main game.
 */
public class GameScreen extends ScreenAdapter {

    private Graphics graphics;
    private PooledEngine engine;
    private AssetManager assetManager;
    private PlayerTurnEndSignal playerTurnEndSignal = new PlayerTurnEndSignal();
    private UserInterfaceStage userInterface;

    public GameScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        engine = new PooledEngine();
        Dungeon dungeon = new LocalDungeonProvider().getNewDungeon(MathUtils.random(Integer.MAX_VALUE - 1), 50, 20);
        graphics = new Graphics(dungeon, assetManager);
        addEntityListeners();
        GameEntities gameEntities = new GameEntities(engine, dungeon, assetManager);
        gameEntities.createInitialEntities();
        EntitySystems entitySystems = new EntitySystems(assetManager, graphics.getModelInstanceRenderer(), dungeon, graphics.getCamera());
        entitySystems.addSystemsTo(engine);
        playerTurnEndSignal.add(entitySystems.getNpcAiSystem());
        userInterface = new UserInterfaceStage(assetManager.get(AssetPaths.SKIN, Skin.class), engine, playerTurnEndSignal);
        setInputProcessors();
    }

    private void setInputProcessors() {
        Entity player = getControllableEntityFrom(engine);
        KeyboardInput keyboardInput = new KeyboardInput(playerTurnEndSignal, player, engine);
        Gdx.input.setInputProcessor(new InputMultiplexer(userInterface, keyboardInput, new TouchInput(player)));
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
        engine.addEntityListener(
                Family.all(PointLightComponent.class).get(),
                new LightComponentListener(graphics.getModelInstanceRenderer().getEnvironment()));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        graphics.begin();
        graphics.render();
        graphics.end();
        userInterface.act(delta);
        userInterface.draw();
    }

    @Override
    public void resize(int width, int height) {
        graphics.resize(width, height);
        userInterface.getViewport().update(width, height);
    }
}
