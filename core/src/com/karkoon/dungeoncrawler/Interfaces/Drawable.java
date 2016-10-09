package com.karkoon.dungeoncrawler.Interfaces;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by @Karkoon on 2016-08-26.
 * Used for enemies and items on ground. Uses Decals.
 */
public interface Drawable {
    void draw(DecalBatch batch, Vector3 lookAt);
}
