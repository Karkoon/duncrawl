package ashlified.graphics;

import ashlified.AssetPaths;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

/**
 * Created by karkoon on 26.04.17.
 */
public class ModelInstanceRenderer implements Disposable {

    private Environment environment;
    private ModelBatch modelBatch;
    private ArrayList<ModelInstance> constantModelInstances;
    private ArrayList<ModelInstance> varyingModelInstances;
    private ModelCache cache;

    private Camera camera;
    private AssetManager manager;

    ModelInstanceRenderer(Camera camera, AssetManager manager) {
        this.camera = camera;
        this.manager = manager;
        this.cache = new ModelCache(new ModelCache.Sorter(), new ModelCache.SimpleMeshPool());
        this.environment = new Environment();
        this.modelBatch = new ModelBatch(new DefaultShaderProvider(createShaderConfig()));
        this.varyingModelInstances = new ArrayList<>();
        this.constantModelInstances = new ArrayList<>();
    }

    public Camera getCamera() {
        return camera;
    }

    private DefaultShader.Config createShaderConfig() {
        DefaultShader.Config config = new DefaultShader.Config();
        config.numPointLights = 1;
        config.fragmentShader = manager.get(AssetPaths.FRAGMENT_SHADER, String.class);
        config.vertexShader = manager.get(AssetPaths.VERTEX_SHADER, String.class);
        return config;
    }

    public void render() {
        cache.begin(camera);
        for (int i = 0; i < constantModelInstances.size(); i++) {
            ModelInstance modelInstance = constantModelInstances.get(i);
            if (isSectionVisible(camera, modelInstance)) {
                cache.add(modelInstance);
            }
        }
        for (int i = 0; i < varyingModelInstances.size(); i++) {
            ModelInstance modelInstance = varyingModelInstances.get(i);
            if (isVisible(camera, modelInstance)) {
                cache.add(modelInstance);
            }
        }
        cache.end();
        varyingModelInstances.clear();
        modelBatch.begin(camera);
        modelBatch.render(cache, environment);
        modelBatch.end();
    }

    private boolean isVisible(Camera cam, ModelInstance instance) {
        float[] val = instance.transform.val;
        return cam.frustum.boundsInFrustum(
                val[Matrix4.M03], val[Matrix4.M13], val[Matrix4.M23],
                5f, 5f, 0f) && cam.position.dst(val[Matrix4.M03], val[Matrix4.M13], val[Matrix4.M23]) < 55f;
    }

    private boolean isSectionVisible(Camera cam, ModelInstance section) {
        float[] val = section.transform.val;
        return cam.frustum.boundsInFrustum(val[Matrix4.M03], val[Matrix4.M13], val[Matrix4.M23],
                5f, 7.5f, 5f) && cam.position.dst(val[Matrix4.M03], val[Matrix4.M13], val[Matrix4.M23]) < 55f;
    }

    public void addToCache(ModelInstance modelInstance) {
        varyingModelInstances.add(modelInstance);
    }

    public void addToCache(ArrayList<ModelInstance> modelInstances) {
        varyingModelInstances.addAll(modelInstances);
    }

    public void addToConstantCache(ArrayList<ModelInstance> modelInstances) {
        constantModelInstances.addAll(modelInstances);
    }

    @Override
    public void dispose() {
        cache.dispose();
    }

    public Environment getEnvironment() {
        return environment;
    }
}