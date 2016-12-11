package com.karkoon.dungeoncrawler.Interfaces;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelCache;

/**
 * Created by @Karkoon on 2016-08-26.
 * Used by points and walls.
 */
public interface Cacheable {
    void cache(ModelCache cache, Environment environment);
}
