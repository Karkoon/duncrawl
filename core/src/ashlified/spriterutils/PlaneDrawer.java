package ashlified.spriterutils;

import ashlified.Graphics;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

import static java.lang.Math.atan2;

public class PlaneDrawer extends Drawer<ModelInstance> {

    private Graphics graphics;

    public PlaneDrawer(Loader<ModelInstance> loader) {
        super(loader);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }


    @Override
    public void draw(Timeline.Key.Object object) {
        ModelInstance instance = this.loader.get(object.ref);
        Vector3 cam = graphics.getCamera().position;
        float posX = object.position.x;
        float posZ = object.position.y;
        float angle = (float) Math.toDegrees(atan2(cam.x - posX, cam.z - posZ));
        instance.transform.setToRotation(new Vector3(0, 1f, 0), angle);
        instance.transform.setTranslation(posX,  0, posZ);
        graphics.getModelInstanceRenderer().addToCache(instance);

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
