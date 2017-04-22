package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class ItemTypeComponent implements Component {

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    enum Type {
        ARMOR, BOOT, GLOVE, HELMET, PENDANT, RING, RUBBISH, TROUSERS
    }

}
