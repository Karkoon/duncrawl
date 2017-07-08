package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.dungeon.DungeonSectionRepresentation;
import ashlified.dungeon.WallModelsAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @Karkoon on 2016-08-24.
 * Creates an environment for rendering and drawing game objects.
 */

class Graphics {

    private Viewport viewport;
    private SpriteBatch fboBatch;
    private FrameBuffer frameBuffer;

    private ModelInstanceRenderer modelInstanceRenderer;

    Graphics(Dungeon dungeon) {
        setUpViewport();
        setUpFrameBuffer();
        modelInstanceRenderer = new ModelInstanceRenderer(viewport.getCamera());
        modelInstanceRenderer.addToStaticCache(new DungeonRepresentation(dungeon));
        Vector2 point = dungeon.getSpawnDungeonSection().getPoint();
        viewport.getCamera().position.set(point.x, DungeonSectionRepresentation.getHeight() / 2f, point.y);
    }

    void resizeViewport(int screenWidth, int screenHeight) {
        viewport.update(screenWidth, screenHeight);
        viewport.getCamera().update();
        setUpFrameBuffer();
    }

    void begin() {
        frameBuffer.begin();
    }

    void end() {
        frameBuffer.end();
        fboBatch.begin();
        fboBatch.draw(frameBuffer.getColorBufferTexture(), 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight(), 0, 0, 1, 1);
        fboBatch.end();
    }

    void render(float delta) {
        clearScreen();
        modelInstanceRenderer.render();
    }

    public Camera getCamera() {
        return viewport.getCamera();
    }

    private void setUpViewport() {
        PerspectiveCamera camera = new PerspectiveCamera(90, 300, 300);
        camera.near = 1f;
        camera.far = 55f;
        viewport = new ExtendViewport(300, 300, camera);
    }

    private void setUpFrameBuffer() {
        if (frameBuffer != null) frameBuffer.dispose();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, 1280 / 6, 720 / 6, true);
        frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        if (fboBatch != null) fboBatch.dispose();
        fboBatch = new SpriteBatch();
    }

    private void clearScreen() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
    }

    public ModelInstanceRenderer getModelInstanceRenderer() {
        return modelInstanceRenderer;
    }

    public static class DungeonRepresentation implements RenderableProvider {

        private List<DungeonSectionRepresentation> models = new ArrayList<>();

        DungeonRepresentation(Dungeon dungeon) {
            WallModelsAccessor accessor = Assets.getWallModelsAccessor();
            for (DungeonSection section : dungeon.getGrid()) {
                models.add(new DungeonSectionRepresentation(section, accessor));
            }
        }

        @Override
        public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
            for (DungeonSectionRepresentation representation : models) {
                representation.getModelInstance().getRenderables(renderables, pool);
            }
        }
    }
}
