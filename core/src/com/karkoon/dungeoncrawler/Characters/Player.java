package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Layers.Layer;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by @Karkoon on 2016-08-30.
 */
public class Player extends Character {

    public static int MAX_ITEMS = 16;
    public static int MAX_USED_ITEMS = 6;

    private Layer layer;

    public Player(Dungeon.DungeonSection startingPosition, Layer layer) {
        super(startingPosition, new Statistics(10, 10, 0, 0, 5, 5, 5),
                new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg"))));
        this.layer = layer;
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
    }

    public void putToUsedItems(Item item) {
        super.putToUsedItems(item);
        MAX_ITEMS++;
    }

    public void removeFromUsedItems(Item item) {
        super.removeFromUsedItems(item);
        MAX_ITEMS--;
    }

    @Override
    protected int getMaxItems() {
        return MAX_ITEMS;
    }

    @Override
    public void processTurn() {
        layer.processTurn();
    }

    @Override
    public Dungeon.DungeonSection moveToward() {
        Dungeon.DungeonSection section = super.moveToward();
        processTurn();
        return section;
    }

    @Override
    public Dungeon.DungeonSection moveBack() {
        Dungeon.DungeonSection section = super.moveBack();
        processTurn();
        return section;
    }
}
