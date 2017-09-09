package ashlified.dungeon;

/**
 * Created by karkoon on 01.02.17.
 * Provides the dungeon from various sources (i.e. from the internet, generated on run-time or read from disk).
 */
public interface DungeonProvider {

    Dungeon getNewDungeon(int seed, int size, int rooms);

}
