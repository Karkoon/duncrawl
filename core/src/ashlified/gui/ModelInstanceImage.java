package ashlified.gui;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

class ModelInstanceImage extends Image {

  public ModelInstanceImage(ModelInstance modelInstance) {
    super((((TextureAttribute) modelInstance.getRenderable(new Renderable()).material.get(TextureAttribute.Diffuse)).textureDescription.texture));
  }

}
