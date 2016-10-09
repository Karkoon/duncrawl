package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Items.Items;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Pc on 2016-09-01.
 */
public class SkeletonTwo extends Enemy {
    public SkeletonTwo(Dungeon.DungeonSection startingPosition) {
        super(startingPosition, new Statistics(3, 0, 0, 1, 3, 3, 0), Items.getRandomItem(), new TextureRegion(new Texture(Gdx.files.internal("skelly.png"))));
    }
}
