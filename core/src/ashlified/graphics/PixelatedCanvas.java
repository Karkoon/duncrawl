package ashlified.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Used for rendering into a small resolution frame buffer for later upscaling to get a pixelated filter effect.
 */
class PixelatedCanvas {

  private SpriteBatch fboBatch;
  private FrameBuffer frameBuffer;
  private Viewport viewport;

  PixelatedCanvas(Viewport viewport) {
    this.viewport = viewport;
    setUpFrameBuffer();
  }

  private void setUpFrameBuffer() {
    if (frameBuffer != null) frameBuffer.dispose();
    int virtualWidth = 1280 / 6;
    int virtualHeight = 720 / 6;
    GLFrameBuffer.FrameBufferBuilder bufferBuilder = new GLFrameBuffer.FrameBufferBuilder(virtualWidth, virtualHeight);
    bufferBuilder.addBasicDepthRenderBuffer();
    bufferBuilder.addBasicColorTextureAttachment(Pixmap.Format.RGB888);
    frameBuffer = bufferBuilder.build();
    frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    if (fboBatch != null) fboBatch.dispose();
    fboBatch = new SpriteBatch();
  }

  void begin() {
    frameBuffer.begin();
  }

  void end() {
    frameBuffer.end();
  }

  void draw() {
    fboBatch.begin();
    int screenWidth = viewport.getScreenWidth();
    int screenHeight = viewport.getScreenHeight();
    Texture colorBufferTexture = frameBuffer.getColorBufferTexture();
    fboBatch.draw(colorBufferTexture, 0, 0, screenWidth, screenHeight, 0, 0, 1, 1);
    fboBatch.end();
  }

  void resize() {
    setUpFrameBuffer();
  }
}
