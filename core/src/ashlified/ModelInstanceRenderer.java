package ashlified;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by karkoon on 26.04.17.
 */
public class ModelInstanceRenderer {

    private Environment environment;
    private ModelBatch modelBatch;
    private ArrayList<RenderableProvider> constantRenderableProviders;
    private ArrayList<RenderableProvider> varyingRenderableProviders;
    private ModelCache cache;

    private PointLight pointLight; // TODO: 21.06.17 create a player with point light and remove this point light

    private Camera camera;

    ModelInstanceRenderer(Camera camera) {
        this.camera = camera;
        this.cache = new ModelCache();
        this.environment = createEnvironment();
        this.modelBatch = new ModelBatch(new DefaultShaderProvider(createShaderConfig()));
        this.varyingRenderableProviders = new ArrayList<>();
        this.constantRenderableProviders = new ArrayList<>();
    }

    public Camera getCamera() {
        return camera;
    }

    private Environment createEnvironment() {
        Environment environment = new Environment();
        pointLight = new PointLight();
        environment.add(pointLight.set(255, 255, 255, camera.position, 100));
        return environment;
    }

    private DefaultShader.Config createShaderConfig() {
        DefaultShader.Config config = new DefaultShader.Config();
        config.numPointLights = 1;
        config.fragmentShader = Gdx.files.internal("shaders/test.fragment.glsl").readString();
        config.vertexShader = Gdx.files.internal("shaders/test.vertex.glsl").readString(); // TODO: change that to asset manager
        return config;
    }

    public void render() {
        pointLight.setPosition(camera.position.x, camera.position.y, camera.position.z);
        cache.begin();
        cache.add(constantRenderableProviders);
        cache.add(varyingRenderableProviders);
        cache.end();
        varyingRenderableProviders.clear();
        modelBatch.begin(camera);
        modelBatch.render(cache, environment);
        modelBatch.end();
    }

    public void addToCache(RenderableProvider... provider) {
        varyingRenderableProviders.addAll(Arrays.asList(provider));
    }

    public void addToStaticCache(RenderableProvider... provider) {
        constantRenderableProviders.addAll(Arrays.asList(provider));
    }
}