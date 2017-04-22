package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class AttackComponent implements Component {

    private int attack = 0;

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
