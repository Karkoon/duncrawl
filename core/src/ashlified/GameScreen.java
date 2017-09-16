package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.HttpDungeonProvider;
import ashlified.entitycomponentsystem.components.PointLightComponent;
import ashlified.entitycomponentsystem.entityinitializers.GameEntities;
import ashlified.entitycomponentsystem.entitylisteners.LightComponentListener;
import ashlified.entitycomponentsystem.entitysystems.*;
import ashlified.entitycomponentsystem.signals.TurnEndSignal;
import ashlified.graphics.Graphics;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by @Karkoon on 2016-08-30.
 * Manages the main game.
 */
public class GameScreen implements Screen {

    private Graphics graphics;
    private PooledEngine engine;
    private AssetManager assetManager;
    private Dungeon dungeon;

    public GameScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        engine = new PooledEngine();
        dungeon = new HttpDungeonProvider().getNewDungeon(MathUtils.random(Integer.MAX_VALUE - 1), 50, 20);
        graphics = new Graphics(dungeon, assetManager);
        addEntityListeners();
        new GameEntities(engine, dungeon, assetManager).createInitialEntities();
        addSystems();
    }

    private void addSystems() {
        NpcRenderingSystem npcRendering = new NpcRenderingSystem(assetManager, graphics.getModelInstanceRenderer());
        ModelInstanceRenderingSystem modelInstanceRendering = new ModelInstanceRenderingSystem(graphics.getModelInstanceRenderer());
        ModelAnimationSystem modelAnimation = new ModelAnimationSystem();
        LightingSystem lighting = new LightingSystem();
        NpcAiSystem npcAi = new NpcAiSystem(dungeon);
        TargetSystem targeting = new TargetSystem();

        TurnEndSignal endTurnSignal = new TurnEndSignal();
        endTurnSignal.add(npcAi);
        InputSystem input = new InputSystem(graphics.getCamera(), endTurnSignal);

        engine.addSystem(npcRendering);
        engine.addSystem(modelAnimation);
        engine.addSystem(modelInstanceRendering);
        engine.addSystem(input);
        engine.addSystem(lighting);
        engine.addSystem(npcAi);
        engine.addSystem(targeting);
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

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
