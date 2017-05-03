package ashlified.spriterutils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

public class DecalDrawer extends Drawer<Decal> {

    private DecalBatch batch;
    private final Camera camera;

    public DecalDrawer(Loader<Decal> loader, DecalBatch batch, Camera camera) {
        super(loader);
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    public void draw(Timeline.Key.Object object) {
        Decal decal = this.loader.get(object.ref);
        float newPivotX = decal.getWidth() * object.pivot.x;
        float newX = object.position.x - newPivotX;
        float newPivotY = decal.getHeight() * object.pivot.y;
        float newY = object.position.y - newPivotY;
        decal.setX(newX);
        decal.setZ(newY);
        decal.lookAt(camera.position, Vector3.Y);
        decal.rotateZ(object.angle);
        decal.setColor(1.0F, 1.0F, 1.0F, object.alpha);
        decal.setScale(1, 1);
        decal.getTextureRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.add(decal);
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