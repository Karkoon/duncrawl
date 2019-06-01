package ashlified;

import ashlified.loading.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by @Karkoon on 18.08.2016.
 * Class interfacing with libgdx framework. Manages screens.
 */

public class DungeonCrawler extends Game {

  public AssetManager assetManager = new AssetManager();

  @Override
  public void create() {
    setScreen(new LoadingScreen(this));
  }


  @Override
  public void dispose() {
    assetManager.dispose();
    super.dispose();
  }
}