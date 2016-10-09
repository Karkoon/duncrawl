package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.karkoon.dungeoncrawler.Interfaces.Renderable;

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

    private ArrayList<com.karkoon.dungeoncrawler.Interfaces.Renderable> renderables;
    private ArrayList<com.karkoon.dungeoncrawler.Interfaces.Drawable> drawables;

    public Graphics(ArrayList<com.karkoon.dungeoncrawler.Interfaces.Renderable> renderables, ArrayList<com.karkoon.dungeoncrawler.Interfaces.Drawable> drawables) {
        this.renderables = renderables;
        this.drawables = drawables;
        setUpLights();
        PerspectiveCamera camera = new PerspectiveCamera(67, 300, 300);
        camera.near = 1f;
        camera.far = 400;
        viewport = new ExtendViewport(300, 300, camera);
        batch = new DecalBatch(new CameraGroupStrategy(viewport.getCamera()));
        mBatch = new ModelBatch();
        initializeFBO();
    }

    public void doItsThing() {
        fbo.begin();
        clearScreen();
        render();
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

    private void render() {
        mBatch.begin(viewport.getCamera());
        for (Renderable renderable : renderables) {
            renderable.render(mBatch, environment);
        }
        mBatch.end();
    }

    private void draw() {
        for (com.karkoon.dungeoncrawler.Interfaces.Drawable drawable : drawables) {
            drawable.draw(batch, getCamera().position);
        }
        batch.flush();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.06f, .06f, .06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}
