package ashlified.graphics;

import ashlified.dungeon.Dungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by @Karkoon on 2016-08-24.
 * Provides a simple interface for rendering the end result.
 */

public class Graphics implements Disposable {

    private Viewport viewport;
    private PixelatedCanvas pixelatedCanvas;

    private ModelInstanceRenderer modelInstanceRenderer;

    public Graphics(Dungeon dungeon, AssetManager manager) {
        setUpViewport();
        pixelatedCanvas = new PixelatedCanvas(viewport);
        modelInstanceRenderer = new ModelInstanceRenderer(viewport.getCamera(), manager);
        modelInstanceRenderer.addToConstantCache(new DungeonRepresentation(dungeon, manager).getModelInstances());
    }

    public void resize(int screenWidth, int screenHeight) {
        viewport.update(screenWidth, screenHeight);
        viewport.getCamera().update();
        pixelatedCanvas.resize();
    }

    public void begin() {
        pixelatedCanvas.begin();
    }

    public void end() {
        pixelatedCanvas.end();
        pixelatedCanvas.draw();
    }

    public void render() {
        clearScreen();
        modelInstanceRenderer.render();
    }

    public Camera getCamera() {
        return viewport.getCamera();
    }

    private void setUpViewport() {
        PerspectiveCamera camera = new PerspectiveCamera(66, 300, 300);
        camera.near = 2f;
        camera.far = 46f;
        viewport = new ExtendViewport(300, 300, camera);
    }

    private void clearScreen() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
    }

    public ModelInstanceRenderer getModelInstanceRenderer() {
        return modelInstanceRenderer;
    }

    @Override
    public void dispose() {
        modelInstanceRenderer.dispose();
    }
}
