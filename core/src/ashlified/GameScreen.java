package ashlified;

import ashlified.components.AnimationsComponent;
import ashlified.components.PositionComponent;
import ashlified.dungeon.Dungeon;
import ashlified.dungeon.HTTPDungeonProvider;
import ashlified.systems.NPCRenderingSystem;
import ashlified.systems.NPCSystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-30.
 */
public class GameScreen implements Screen {

    private Graphics graphics;
    private PooledEngine engine;

    @Override
    public void show() {
        engine = new PooledEngine();
        Dungeon dungeon = new HTTPDungeonProvider().getNewDungeon(new Random().nextInt(), 200, 30);
        graphics = new Graphics(dungeon);
        Gdx.input.setInputProcessor(new InputAdapter() {
            Vector3 moveRate = new Vector3(0, 0, -10);

            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        graphics.getCamera().position.add(moveRate);
                        Gdx.app.log("wtf", "wtf");
                        break;
                    case Input.Keys.S:
                        graphics.getCamera().position.sub(moveRate);
                        break;
                    case Input.Keys.A:
                        graphics.getCamera().rotate(Vector3.Y, 90);
                        moveRate.rotate(Vector3.Y, 90);
                        break;
                    case Input.Keys.D:
                        graphics.getCamera().rotate(Vector3.Y, -90);
                        moveRate.rotate(Vector3.Y, -90);
                        break;
                }
                graphics.getCamera().update();
                return true;
            }
        });// todo input multiplexer of userIntwwerface and some sort of camera control.
        engine.addSystem(new NPCSystem(dungeon));
        engine.addSystem(new NPCRenderingSystem(Family.all(AnimationsComponent.class, PositionComponent.class).get(), graphics));
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
