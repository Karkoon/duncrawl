package ashlified.spriterutils;

import ashlified.Graphics;
import ashlified.dungeon.DungeonSection;
import ashlified.dungeon.DungeonSectionModel;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

public class DecalDrawer extends Drawer<Decal> {

    private Graphics graphics;

    public DecalDrawer(Loader<Decal> loader) {
        super(loader);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(Timeline.Key.Object object) {
        Decal decal = this.loader.get(object.ref);
        decal.setPosition(object.position.x, DungeonSectionModel.getHeight()/2.05f, object.position.y);
        decal.lookAt(graphics.getCamera().position, Vector3.Y);
        //decal.rotateZ(object.angle);
        decal.setColor(1.0F, 1.0F, 1.0F, object.alpha);
        decal.setScale(0.45f, 0.45f);
        decal.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        decal.getTextureRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        graphics.addDecal(decal);
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