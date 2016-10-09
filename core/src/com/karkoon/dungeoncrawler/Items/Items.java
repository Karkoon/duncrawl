package com.karkoon.dungeoncrawler.Items;

import com.karkoon.dungeoncrawler.Items.Armors.Armor;
import com.karkoon.dungeoncrawler.Items.Boots.Boots;
import com.karkoon.dungeoncrawler.Items.Gloves.Gloves;
import com.karkoon.dungeoncrawler.Items.Helmets.Helmet;
import com.karkoon.dungeoncrawler.Items.Pendants.Pendant;
import com.karkoon.dungeoncrawler.Items.Rings.Ring;
import com.karkoon.dungeoncrawler.Items.Rings.RingOfHealth;
import com.karkoon.dungeoncrawler.Items.Trousers.Trousers;

import java.util.Random;

/**
 * Created by Roksana on 03.09.2016.
 */
public final class Items {

    public static Class[] baseTypes = {Ring.class, Armor.class, Boots.class, Gloves.class, Helmet.class, Pendant.class, Trousers.class};

    private Items() {
    }

    public static Item getRandomItem() {
        int randomType = new Random().nextInt(2);
        switch (randomType) {
            case 0:
                return new RingOfHealth();
            case 1:
                return new com.karkoon.dungeoncrawler.Items.Rubbish.Stone();
        }
        return new com.karkoon.dungeoncrawler.Items.Rubbish.Stone();
    }
}
