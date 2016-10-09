package com.karkoon.dungeoncrawler.Interfaces;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

/**
 * Created by @Karkoon on 2016-08-26.
 * Used by points and walls.
 */
public interface Renderable {
    void render(ModelBatch batch, Environment environment);
}
