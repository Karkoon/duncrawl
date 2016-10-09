package com.karkoon.dungeoncrawler.Interfaces;

import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Items.Item;

import java.util.Collection;

/**
 * Created by Roksana on 03.09.2016.
 */
public interface Container {

    /**
     * Adds/puts/assigns an item to the container.
     *
     * @param item to contain
     * @return true if successful.
     * @implNote The Item should be ideally stored in a Collection.
     */
    boolean putItem(Item item);

    /**
     * Used for getting a reference to an instance of a type of an Item.
     *
     * @param name of an Item to be obtained.
     * @return Item inside the container if it's inside the container. Null otherwise.
     * @implNote Requested item shouldn't be removed from the container.
     */
    Item getItem(String name);

    /**
     * Removes the item from the container.
     *
     * @param item     is a specific item to be dropped.
     * @param position is the section of the dungeon where the item is going to be dropped.
     * @implSpec All references to the item by the container should be removed.
     */
    default void dropItem(Item item, Dungeon.DungeonSection position) {
        item.drop(position);
    }

    /**
     * Returns a Collection of all the items in the container.
     *
     * @return Collection of all Items in the container. Empty collection if container is empty.
     */
    Collection<Item> getItems();
}
