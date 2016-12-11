package com.karkoon.dungeoncrawler.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Pc on 2016-12-11.
 */
public class HealthBar extends ProgressBar {

    private Character character;

    HealthBar(Character character, Skin skin) {
       super(0, character.getStatistics().get(Statistics.AttributeType.MAX_HEALTH).getValue(), 1, false, skin);
        this.character = character;
        setSize(200, 100);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(getStage().getWidth() - 250,
                getStage().getHeight() - (getStage().getHeight() - 50));
        setRange(0, character.getStatistics().get(Statistics.AttributeType.MAX_HEALTH).getValue());
        setValue(character.getStatistics().get(Statistics.AttributeType.CURRENT_HEALTH).getValue());
    }
}
