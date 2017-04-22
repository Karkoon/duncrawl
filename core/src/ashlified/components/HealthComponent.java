package ashlified.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by karkoon on 25.03.17.
 */
public final class HealthComponent implements Component {

    private int health;
    private int maxHealth;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
