package ashlified.graphics.spriterutils;

import ashlified.graphics.ModelInstanceRenderer;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FlushablePool;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

import java.util.ArrayList;

import static java.lang.Math.atan2;

/**
 * Draws 2D models of Spriter-created animated NPCs.
 */
public class PooledPlaneAtlasDrawer extends Drawer<FlushablePool<ModelInstance>> {

    private final ModelInstanceRenderer renderer;
    private ArrayList<FlushablePool> poolsToFlushAfterFrame = new ArrayList<>();

    public PooledPlaneAtlasDrawer(Loader<FlushablePool<ModelInstance>> loader, ModelInstanceRenderer renderer) {
        super(loader);
        this.renderer = renderer;
    }

    @Override
    public void draw(Timeline.Key.Object object) {
        FlushablePool<ModelInstance> pool = this.loader.get(object.ref);
        poolsToFlushAfterFrame.add(pool);
        ModelInstance instance = pool.obtain();
        float posX = object.position.x;
        float posZ = object.position.y;
        Vector3 cam = renderer.getCamera().position;
        float angle = (float) Math.toDegrees(atan2(cam.x - posX, cam.z - posZ));
        instance.transform.setToRotation(0, 1f, 0, angle);
        instance.transform.setTranslation(posX, 0, posZ);
        renderer.addToCache(instance);
    }

    public void flush() {
        for (int i = 0; i < poolsToFlushAfterFrame.size(); i++) {
            FlushablePool pool = poolsToFlushAfterFrame.get(i);
            pool.flush();
        }
        poolsToFlushAfterFrame.clear();
    }

    public void setColor(float r, float g, float b, float a) {
    }

    public void rectangle(float x, float y, float width, float height) {
    }

    public void line(float x1, float y1, float x2, float y2) {
    }

    public void circle(float x, float y, float radius) {
    }
}
