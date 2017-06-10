package ashlified;

import ashlified.dungeon.Dungeon;
import ashlified.dungeon.DungeonSection;
import ashlified.dungeon.DungeonSectionRepresentation;
import ashlified.dungeon.WallModelsAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

/**
 * Created by @Karkoon on 2016-08-24.
 * Creates an environment for rendering and drawing game objects.
 */

public class Graphics {

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
        //decalBatch = new DecalBatch(new CameraGroupStrategy(getCamera()));
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
    }

    public ModelInstanceRenderer getModelInstanceRenderer() {
        return modelInstanceRenderer;
    }

    /**
     * Created by karkoon on 26.04.17.
     */
    public static class ModelInstanceRenderer {

        private Environment environment;
        private ModelBatch modelBatch;
        private ArrayList<RenderableProvider> staticInstances;
        private Deque<RenderableProvider> variableInstances;
        private ModelCache cache;

        private PointLight pointLight;

        private Camera camera;

        ModelInstanceRenderer(Camera camera) {
            this.camera = camera;
            this.cache = new ModelCache();
            setUpEnvironment();
            pointLight = new PointLight();
            environment.add(pointLight.set(255,255,255, camera.position, 110));
            DefaultShader.Config config = new DefaultShader.Config();
            config.numPointLights = 1;
            config.fragmentShader = Gdx.files.internal("shaders/test.fragment.glsl").readString();
            config.vertexShader = Gdx.files.internal("shaders/test.vertex.glsl").readString();
            modelBatch = new ModelBatch(new DefaultShaderProvider(config));
            this.variableInstances = new ArrayDeque<>();
            staticInstances = new ArrayList<>();
        }

        public void render() {
            pointLight.setPosition(camera.position.x, camera.position.y, camera.position.z);
            cache.begin();
            cache.add(staticInstances);
            cache.add(variableInstances);
            cache.end();
            variableInstances.clear();
            modelBatch.begin(camera);
            modelBatch.render(cache, environment);
            modelBatch.end();
        }

        private void setUpEnvironment() {
            environment = new Environment();
            //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        }

        public void addToCache(ModelInstance... modelInstances) {
            variableInstances.addAll(Arrays.asList(modelInstances));
        }

        public void addToCache(RenderableProvider... provider) {
            variableInstances.addAll(Arrays.asList(provider));
        }

        public void addToStaticCache(ModelInstance... modelInstances) {
            staticInstances.addAll(Arrays.asList(modelInstances));
        }

        public void addToStaticCache(RenderableProvider... provider) {
            staticInstances.addAll(Arrays.asList(provider));
        }
    }

    public static class DungeonRepresentation implements RenderableProvider {

        List<DungeonSectionRepresentation> models = new ArrayList<>();

        public DungeonRepresentation(Dungeon dungeon) {
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
