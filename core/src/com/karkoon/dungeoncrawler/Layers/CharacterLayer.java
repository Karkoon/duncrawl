package com.karkoon.dungeoncrawler.Layers;

import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Characters.Player;
import com.karkoon.dungeoncrawler.Characters.SkeletonTwo;
import com.karkoon.dungeoncrawler.Dungeon;

/**
 * Created by @Karkoon on 2016-08-27.
 */
public class CharacterLayer extends Layer {

    Character mainCharacter;

    public CharacterLayer(Dungeon dungeon) {
        super();
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addNPC(new SkeletonTwo(dungeon.getRandomDungeonSection()));
        addMainCharacter(new Player(dungeon.getSpawnDungeonSection(), this));
       /* Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                processTurn();
            }
        }, 3, 3);*/
    }

    public Character getMainCharacter() {
        return mainCharacter;
    }

    private void addMainCharacter(Character character) {
        addUpdateable(character);
        mainCharacter = character;
    }
}
