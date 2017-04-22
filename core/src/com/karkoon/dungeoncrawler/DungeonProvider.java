package com.karkoon.dungeoncrawler;

/**
 * Created by karkoon on 01.02.17.
 */
public interface DungeonProvider {

    Dungeon getNewDungeon(int seed, int size, int rooms);

}
