package com.karkoon.dungeoncrawler.Layers;

import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Items.Items;

/**
 * Created by Roksana on 03.09.2016.
 */
public class ItemLayer extends Layer {
    public ItemLayer(Dungeon dungeon) {
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
        addItem(Items.getRandomItem().drop(dungeon.getRandomDungeonSection()));
    }
}
