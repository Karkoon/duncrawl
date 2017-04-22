package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class ArmorComponent implements Component {

    private int armor = 0;

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
