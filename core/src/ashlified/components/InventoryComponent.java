package ashlified.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

/**
 * Created by karkoon on 25.03.17.
 */
public final class InventoryComponent implements Component {

    private ArrayList<Entity> items = new ArrayList<>();
    private ArrayList<Entity> usedItems = new ArrayList<>();
    private int maxItems;

    public ArrayList<Entity> getItems() {
        return items;
    }

    public void setItems(ArrayList<Entity> items) {
        this.items = items;
    }

    public ArrayList<Entity> getUsedItems() {
        return usedItems;
    }

    public void addUsedItem(Entity item) {
        this.usedItems.add(item);
    }

    public void clearUsedItems() {
        this.usedItems.clear();
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

}
