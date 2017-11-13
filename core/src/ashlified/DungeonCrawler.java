package ashlified;

import ashlified.loading.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

/**
 * Created by @Karkoon on 18.08.2016.
 * Class interfacing with libgdx framework. Manages screens.
 */

public class DungeonCrawler extends Game {

    public AssetManager assetManager = new AssetManager();

    private int time = 0;
    private GLProfiler glProfiler;
    @Override
    public void create() {
         glProfiler = new GLProfiler(Gdx.graphics);
        glProfiler.enable();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        manageProfiler();
        super.render();
    }

    /**
     * PROFILER STUFF
     */
    private void printProfilerInfo() {
        Gdx.app.error("Profiler",
                "Calls: " + glProfiler.getCalls()
                        + " DrawCalls: " + glProfiler.getDrawCalls()
                        + " ShaderSwitches: " + glProfiler.getShaderSwitches()
                        + " Texture Bindings: " + glProfiler.getTextureBindings()
                        + " Vertex count: " + glProfiler.getVertexCount().average);
    }

    private void manageProfiler() {
        if (time == 10) {
            printProfilerInfo();
            time = 0;
        } else {
            time++;
        }
        glProfiler.reset();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        super.dispose();
    }
}