package ashlified;

import ashlified.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by @Karkoon on 18.08.2016.
 * Class interfacing with libgdx framework. It manages screens.
 */

public class DungeonCrawler extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

