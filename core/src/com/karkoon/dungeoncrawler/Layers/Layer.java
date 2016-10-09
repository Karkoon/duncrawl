package com.karkoon.dungeoncrawler.Layers;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Interfaces.Drawable;
import com.karkoon.dungeoncrawler.Interfaces.TurnSupport;
import com.karkoon.dungeoncrawler.Interfaces.Updateable;
import com.karkoon.dungeoncrawler.Items.Item;

import java.util.ArrayList;

/**
 * Created by @Karkoon on 2016-08-27.
 */
public abstract class Layer implements Drawable, Updateable, TurnSupport {

    private ArrayList<Drawable> drawables;
    private ArrayList<Updateable> updateables;
    private ArrayList<TurnSupport> turnSupporting;

    Layer() {
        drawables = new ArrayList<>();
        updateables = new ArrayList<>();
        turnSupporting = new ArrayList<>();
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch, lookAt);
        }
    }

    @Override
    public void update(float delta) {
        for (Updateable updateable : updateables) {
            updateable.update(delta);
        }
    }

    @Override
    public void processTurn() {
        turnSupporting.forEach(TurnSupport::processTurn);
    }

    public void addNPC(Character npc) {
        drawables.add(npc);
        updateables.add(npc);
        turnSupporting.add(npc);
    }

    public void addItem(Item item) {
        drawables.add(item);
    }

    public void addUpdateable(Updateable updateable) {
        updateables.add(updateable);
    }

    public void addTurns(Updateable updateable) {
        updateables.add(updateable);
    }

    public void addDrawable(Updateable updateable) {
        updateables.add(updateable);
    }

    public ArrayList<TurnSupport> getTurnSupporting() {
        return turnSupporting;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

}
