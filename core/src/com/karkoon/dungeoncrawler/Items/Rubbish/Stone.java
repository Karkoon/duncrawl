package com.karkoon.dungeoncrawler.Items.Rubbish;

import com.badlogic.gdx.Gdx;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Roksana on 03.09.2016.
 */
public class Stone extends Item {
    public Stone() {
        super("Stone", "A heavy stone, used for melee or throwing",new Statistics(0,0,0,2,0,0,0), Gdx.files.internal("stone.png"));
    }
}
