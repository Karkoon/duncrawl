package ashlified;

import ashlified.components.PointLightComponent;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.HTTPDungeonProvider;
import ashlified.entitylisteners.LightComponentListener;
import ashlified.graphics.Graphics;
import ashlified.systems.*;
import ashlified.util.RandomNumber;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by @Karkoon on 2016-08-30.
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
        dungeon = new HTTPDungeonProvider().getNewDungeon(RandomNumber.nextInt(), 50, 20);
        graphics = new Graphics(dungeon, assetManager);
        addEntityListeners();
        addSystems();
    }

    private void addSystems() {
        engine.addSystem(new NPCCreationSystem(dungeon, assetManager));
        engine.addSystem(new ChestCreationSystem(dungeon, assetManager));
        engine.addSystem(new NPCRenderingSystem(
                assetManager,
                graphics.getModelInstanceRenderer()));
        engine.addSystem(new ModelInstanceRenderingSystem(graphics.getModelInstanceRenderer()));
        engine.addSystem(new ModelAnimationSystem());
        engine.addSystem(new PlayerSystem(dungeon.getSpawnDungeonSection()));
        engine.addSystem(new InputSystem(graphics.getCamera()));
        engine.addSystem(new LightingSystem());
    }

    private void addEntityListeners() {
        engine.addEntityListener(Family.all(PointLightComponent.class).get(),
                new LightComponentListener(graphics.getModelInstanceRenderer().getEnvironment()));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        graphics.begin();
        graphics.render(delta);
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
