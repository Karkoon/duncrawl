package com.karkoon.dungeoncrawler.Items.Rings;

import com.badlogic.gdx.Gdx;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Pc on 2016-09-11.
 */
public class RingOfHealth extends Ring {
    public RingOfHealth() {
        super("Ring of Health", "Made by ancient fairies in the mysterious land of your ass. Gives +5 to max health.",
                new Statistics(5,0,0,0,0,0,0), Gdx.files.internal("ringofhealth.png"));
    }
}
