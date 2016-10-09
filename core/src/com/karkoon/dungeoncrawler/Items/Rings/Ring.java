package com.karkoon.dungeoncrawler.Items.Rings;

import com.badlogic.gdx.files.FileHandle;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Pc on 2016-09-08.
 */
public abstract class Ring extends Item {
    protected Ring(String name, String description, Statistics stats, FileHandle fileHandle) {
        super(name, description, stats, fileHandle);
    }
}
