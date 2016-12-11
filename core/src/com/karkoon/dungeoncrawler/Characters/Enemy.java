package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by @Karkoon on 2016-08-27.
 */
public abstract class Enemy extends Character {

    private int maxItems = 1;

    public Enemy(Dungeon.DungeonSection startingPosition, Statistics stats, Item item, TextureRegion... regions) {
        super(startingPosition, stats, regions);
        putItem(item);
    }

    @Override
    public int getMaxItems() {
        return maxItems;
    }

    @Override
    public int getMaxUsedItems() {
        return getMaxItems();
    }

    @Override
    public void processTurn() {
        moveToward();
        if (Math.random() > 0.5f) rotateLeft();
        else rotateRight();
    }

    @Override
    public boolean putItem(Item item) {
        boolean itemSuccesfullyPutToEquipment = super.putItem(item);
        if (itemSuccesfullyPutToEquipment) {
            putToUsedItems(item);
        }
        return itemSuccesfullyPutToEquipment;
    }

    @Override
    public void dropItem(Item item, Dungeon.DungeonSection position) {
        super.dropItem(item, position);
        removeFromUsedItems(item);
    }
}
