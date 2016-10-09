package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.Game;

/**
 * Created by @Karkoon on 18.08.2016.
 * Base class.
 */

public class DungeonCrawler extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

}