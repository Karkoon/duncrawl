package com.karkoon.dungeoncrawler.Items.Armors;

import com.badlogic.gdx.files.FileHandle;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Pc on 2016-09-11.
 */
public abstract class Armor extends Item {
    protected Armor(String name, String description, Statistics stats, FileHandle fileHandle) {
        super(name, description, stats, fileHandle);
    }
}
