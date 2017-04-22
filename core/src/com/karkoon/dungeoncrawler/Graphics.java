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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;
import com.karkoon.dungeoncrawler.Interfaces.Drawable;

import java.util.ArrayList;

/**
 * Created by @Karkoon on 2016-08-24.
 * Creates an environment for rendering and drawing game objects.
 */

class Graphics {

    private DecalBatch batch;
    private Viewport viewport;
    private SpriteBatch fboBatch;
    private Environment environment;
    private FrameBuffer frameBuffer;
    private ModelBatch mBatch;
    private ModelCache cache;

    private ArrayList<Cacheable> cacheables;
    private ArrayList<Drawable> drawables;

    Graphics(ArrayList<Cacheable> cacheables, ArrayList<Drawable> drawables) {
        this.cacheables = cacheables;
        this.drawables = drawables;
        setUpEnvironment();
        setUpViewport();
        setUpFrameBuffer();
        setUpModelCache();
        batch = new DecalBatch(new CameraGroupStrategy(viewport.getCamera()));
        mBatch = new ModelBatch();
    }

    void resizeViewport(int screenWidth, int screenHeight) {
        viewport.update(screenWidth, screenHeight);
        viewport.getCamera().update();
        setUpFrameBuffer();
    }

    void update() {
        frameBuffer.begin();
        clearScreen();
        renderModels();
        drawDecals();
        frameBuffer.end();
        fboBatch.begin();
        fboBatch.draw(frameBuffer.getColorBufferTexture(), 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight(), 0, 0, 1, 1);
        fboBatch.end();
    }

    Camera getCamera() {
        return viewport.getCamera();
    }

    private void renderModels() {
        mBatch.begin(viewport.getCamera());
        mBatch.render(cache, environment); //cached models
        mBatch.end();
    }

    private void drawDecals() {
        for (Drawable drawable : drawables) {
            drawable.draw(batch, getCamera().position);
        }
        batch.flush();
    }

    private void setUpViewport() {
        PerspectiveCamera camera = new PerspectiveCamera(67, 300, 300);
        camera.near = 1f;
        camera.far = 1000f;
        viewport = new ExtendViewport(300, 300, camera);
    }

    public void setUpFrameBuffer() {
        if (frameBuffer != null) frameBuffer.dispose();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, 1280 / 6, 720 / 6, true);
        frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        if (fboBatch != null) fboBatch.dispose();
        fboBatch = new SpriteBatch();
    }

    private void setUpEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.8f, -0.4f, -0.2f));
    }

    private void setUpModelCache() {
        cache = new ModelCache();
        cache.begin();
        for (Cacheable cacheable : cacheables) {
            cacheable.cacheModel(cache);
        }
        cache.end();
    }

    private void clearScreen() {
        Gdx.gl20.glClearColor(255f, 88 / 255f, 88 / 255f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}
