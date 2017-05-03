package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.dungeon.DungeonSectionModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by @Karkoon on 2016-08-24.
 * Creates an environment for rendering and drawing game objects.
 */

public class Graphics {

    private Viewport viewport;
    private SpriteBatch fboBatch;
    private FrameBuffer frameBuffer;

    private DungeonRenderer dungeonRenderer;

    Graphics(Dungeon dungeon) {
        setUpViewport();
        setUpFrameBuffer();
        dungeonRenderer = new DungeonRenderer(dungeon, viewport.getCamera());
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
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("skelly.png")), 10,10);
        sprite.setSize(10000, 10000);
        sprite.setColor(Color.BLUE);
        sprite.draw(fboBatch);
        fboBatch.end();
    }

    void render(float delta) {
        clearScreen();
        dungeonRenderer.render(delta);
    }

    Camera getCamera() {
        return viewport.getCamera();
    }

    private void setUpViewport() {
        PerspectiveCamera camera = new PerspectiveCamera(67, 300, 300);
        camera.near = 1f;
        camera.far = 1000f;
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
        Gdx.gl20.glClearColor(50/255f, 50/255f, 50/255f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Created by karkoon on 26.04.17.
     */
    public static class DungeonRenderer {

        private Environment environment;
        private ModelBatch modelBatch;
        private ModelCache cache;

        private ArrayList<DungeonSectionModel> models;
        private Camera camera;

        DungeonRenderer(Dungeon dungeon, Camera camera) {
            this.camera = camera;
            createDungeonSectionModels(dungeon);
            setUpEnvironment();
            setUpModelCache();
            modelBatch = new ModelBatch();
        }

        private void createDungeonSectionModels(Dungeon dungeon) {
            models = new ArrayList<>();
            for (DungeonSection section :
                    dungeon.getGrid()) {
                models.add(new DungeonSectionModel(section));
            }
        }

        public void render(float deltaTime) {
            modelBatch.begin(camera);
            modelBatch.render(cache, environment); //cached models
            modelBatch.end();
        }

        private void setUpEnvironment() {
            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
            environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.8f, -0.4f, -0.2f));
        }

        private void setUpModelCache() {
            cache = new ModelCache();
            cache.begin();
            for (DungeonSectionModel model : models) {
                cache.add(model.getModelInstance());
            }
            cache.end();
        }
    }
}
