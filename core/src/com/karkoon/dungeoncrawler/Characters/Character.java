package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.karkoon.dungeoncrawler.AnimatedDecal;
import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Interfaces.*;
import com.karkoon.dungeoncrawler.Items.Item;
import com.karkoon.dungeoncrawler.Statistics;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by @Karkoon on 2016-08-28.
 */
public abstract class Character implements Drawable, Updateable, TurnSupport, Container, Damageable {

    private Statistics baseStats;
    private Statistics currentStats;
    private Dungeon.DungeonSection position;
    private AnimatedDecal decal;
    private Vector3 moveRate;
    private Vector3 direction;
    private ArrayList<Item> items;

    Character(Dungeon.DungeonSection startingPosition, Statistics statistics, TextureRegion... textureRegions) {
        setDecal(textureRegions);
        setPosition(startingPosition);
        this.baseStats = statistics;
        this.currentStats = new Statistics(0, 0, 0, 0, 0, 0, 0);
        currentStats.add(baseStats);
        moveRate = new Vector3(0, 0, Dungeon.DungeonSection.SIZE);
        direction = new Vector3(0, 0, 90);
        items = new ArrayList<>(getMaxItems());
    }

    public AnimatedDecal getDecal() {
        return decal;
    }

    public abstract int getMaxItems();

    public abstract int getMaxUsedItems();

    private void setDecal(TextureRegion... textureRegions) {
        if (textureRegions == null) throw new NullPointerException("TextureRegions can't be null");
        this.decal = AnimatedDecal.newAnimatedDecal(Dungeon.DungeonSection.SIZE,
                Dungeon.DungeonSection.SIZE, new Animation(1, textureRegions), true);
        this.decal.setKeepSize(true);
    }

    public void setPosition(Dungeon.DungeonSection section) {
        if (section != null) {
            ArrayList<Object> occupyingObjectCopy = new ArrayList<>(section.occupyingObject);
            for (Object object : occupyingObjectCopy) {
                if (object instanceof Damageable) {
                    attack((Damageable) object);
                    return;
                }
                if (object instanceof Item) {
                    Item item = (Item) object;
                    item.pickUp(this);
                }
            }

            if (this.position != null) this.position.occupyingObject.remove(this);
            this.position = section;
            section.occupyingObject.add(this);
        }
    }

    public Dungeon.DungeonSection getPositionSection() {
        return position;
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
        decal.lookAt(lookAt, Vector3.Y);
        decal.update(0);
        batch.add(decal);
    }

    @Override
    public void update(float delta) {
        float yHeight = Dungeon.DungeonSection.SIZE / 2f;
        decal.getPosition().lerp(new Vector3(position.point.x, yHeight, position.point.y), 0.1f);
        decal.update(delta);
    }

    @Override
    public final void damage(int damageValue) {
        Statistics.Attribute armor = currentStats.get(Statistics.AttributeType.ARMOR);
        Statistics.Attribute health = currentStats.get(Statistics.AttributeType.MAX_HEALTH);
        int givenDamage = damageValue - armor.getValue();
        if (givenDamage > 0) {
            health.setValue(health.getValue() - givenDamage);
            Gdx.app.log("damaged, current health", Integer.toString(health.getValue()));
        }
        if (health.getValue() <= 0) die();
    }

    public void die() {
        for (Item item : getItems()) dropItem(item, position);
        Gdx.app.log("kill", this.toString());
    }

    @Override
    abstract public void processTurn();

    public Dungeon.DungeonSection moveToward() {
        Vector2 possibleNextPosition = getPositionSection().point.cpy().add(getMoveRate().x, getMoveRate().z);
        Dungeon.DungeonSection possibleNextSection = getPositionSection().getDungeon().getDungeonSectionAt(possibleNextPosition);
        if (possibleNextSection != null) {
            setPosition(possibleNextSection);
            return possibleNextSection;
        }
        return null;
    }

    public Dungeon.DungeonSection moveBack() {
        Vector2 possibleNextPosition = getPositionSection().point.cpy().sub(getMoveRate().x, getMoveRate().z);
        Dungeon.DungeonSection possibleNextSection = getPositionSection().getDungeon().getDungeonSectionAt(possibleNextPosition);
        if (possibleNextSection != null) {
            setPosition(possibleNextSection);
            return possibleNextSection;
        }
        return null;
    }

    public void rotateLeft() {
        getMoveRate().rotate(Vector3.Y, 90);
        setMoveRate(Math.round(getMoveRate().x), Math.round(getMoveRate().z));
        direction.rotate(Vector3.Y, 90);
    }

    public void rotateRight() {
        getMoveRate().rotate(Vector3.Y, -90);
        getMoveRate().set(Math.round(getMoveRate().x), getMoveRate().y, Math.round(getMoveRate().z));
        direction.rotate(Vector3.Y, -90);
    }

    public void attack(Damageable character) {
        character.damage(currentStats.get(Statistics.AttributeType.BASE_DAMAGE).getValue());
    }

    public void putToUsedItems(Item item) {
        getStatistics().add(item.getStats());
    }

    public void removeFromUsedItems(Item item) {
        getStatistics().subtract(item.getStats());
    }

    public void setMoveRate(float x, float z) {
        this.moveRate = new Vector3(x, 0, z);
    }

    public Vector3 getMoveRate() {
        return moveRate;
    }

    public Statistics getStatistics() {
        return currentStats;
    }

    public Vector3 getDirection() {
        return direction;
    }

    @Override
    public boolean putItem(Item item) {
        if (items.size() < getMaxItems()) {
            items.add(item);
            return true;
        }
        return false;
    }

    @Override
    public void dropItem(Item item, Dungeon.DungeonSection position) {
        items.remove(item);
        item.drop(position);
    }

    @Override
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) return item;
        }
        return null;
    }

    @Override
    public Collection<Item> getItems() {
        return new ArrayList<>(items);
    }

}
