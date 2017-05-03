package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class StatsComponent implements Component {

    private int strength;
    private int dexterity;
    private int wisdom;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

}
