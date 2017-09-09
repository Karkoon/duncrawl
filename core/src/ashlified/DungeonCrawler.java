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

    @Override
    public void create() {
        GLProfiler.enable();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        manageProfiler();
        super.render();
    }

    private void printProfilerInfo() {
        Gdx.app.error("Profiler",
                "Calls: " + GLProfiler.calls
                        + " DrawCalls: " + GLProfiler.drawCalls
                        + " ShaderSwitches: " + GLProfiler.shaderSwitches
                        + " Texture Bindings: " + GLProfiler.textureBindings
                        + " Vertex count: " + GLProfiler.vertexCount.average);
    }

    private void manageProfiler() {
        if (time == 10) {
            printProfilerInfo();
            time = 0;
        } else {
            time++;
        }
        GLProfiler.reset();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        super.dispose();
    }
}