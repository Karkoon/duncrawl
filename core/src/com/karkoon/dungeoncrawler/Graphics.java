package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.karkoon.dungeoncrawler.Interfaces.Drawable;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;

import java.util.ArrayList;

/**
 * Created by @Karkoon on 2016-08-24.
 */
public class Graphics {

    private DecalBatch batch;
    private ExtendViewport viewport;
    private SpriteBatch fboBatch;
    private Environment environment;
    private FrameBuffer fbo;
    private ModelBatch mBatch;
    private ModelCache cache;

    private ArrayList<Cacheable> cacheables;
    private ArrayList<Drawable> drawables;

    public Graphics(ArrayList<Cacheable> cacheables, ArrayList<Drawable> drawables) {
        cache = new ModelCache();
        this.cacheables = cacheables;
        this.drawables = drawables;
        setUpLights();
        PerspectiveCamera camera = new PerspectiveCamera(67, 300, 300);
        camera.near = 1f;
        camera.far = 400f;
        viewport = new ExtendViewport(300, 300, camera);
        batch = new DecalBatch(new CameraGroupStrategy(viewport.getCamera()));
        mBatch = new ModelBatch();
        initializeFBO();
        cacheModels();
    }

    public void step() {
        fbo.begin();
        clearScreen();
        mBatch.begin(viewport.getCamera());
        mBatch.render(cache, environment);
        mBatch.end();
        draw();
        fbo.end();
        fboBatch.begin();
        fboBatch.draw(fbo.getColorBufferTexture(), 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight(), 0, 0, 1, 1);
        fboBatch.end();

    }

    public Camera getCamera() {
        return viewport.getCamera();
    }

    public void resizeViewport(int screenWidth, int screenHeight) {
        viewport.update(screenWidth, screenHeight);
        viewport.getCamera().update();
        initializeFBO();
    }

    public void initializeFBO() {
        if (fbo != null) fbo.dispose();
        fbo = new FrameBuffer(Pixmap.Format.RGB888, 1280 / 6, 720 / 6, true);
        fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        if (fboBatch != null) fboBatch.dispose();
        fboBatch = new SpriteBatch();
    }

    private void setUpLights() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.8f, -0.4f, -0.2f));
    }

    private void cacheModels() {
        cache.begin();
        for (Cacheable cacheable : cacheables) {
            cacheable.cache(cache, environment);
        }
        cache.end();
    }

    private void draw() {
        for (Drawable drawable : drawables) {
            drawable.draw(batch, getCamera().position);
        }
        batch.flush();
    }

    private void clearScreen() {
        Gdx.gl20.glClearColor(88/255f, 88/255f, 88/255f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}
