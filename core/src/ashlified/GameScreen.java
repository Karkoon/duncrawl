package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.HTTPDungeonProvider;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-30.
 */
public class GameScreen implements Screen {

    private Graphics graphics;
    private Engine engine;

    @Override
    public void show() {
        engine = new Engine();
        Dungeon dungeon = new HTTPDungeonProvider().getNewDungeon(new Random().nextInt(), 256, 32);
        graphics = new Graphics(dungeon);
        Gdx.input.setInputProcessor(new CameraInputController(graphics.getCamera()));// todo input multiplexer of userInterface and some sort of camera control.
    }

    @Override
    public void render(float delta) {
        graphics.begin();
        engine.update(delta);
        graphics.render(delta);
        graphics.end();
    }

    @Override
    public void resize(int width, int height) {
        graphics.resizeViewport(width, height);
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
