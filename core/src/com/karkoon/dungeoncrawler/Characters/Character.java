package com.karkoon.dungeoncrawler.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.karkoon.dungeoncrawler.*;
import com.karkoon.dungeoncrawler.Interfaces.*;
import com.karkoon.dungeoncrawler.Items.Item;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by @Karkoon on 2016-08-28.
 */
public abstract class Character implements Drawable, Updateable, TurnSupport, Container, Damageable {

    private Statistics currentStats;
    private DungeonSection position;
    private CharacterAnimations decalAnimation;
    private Vector3 moveRate;
    private Vector3 direction;
    private ArrayList<Item> items;

    Character(DungeonSection startingPosition, Statistics statistics, TextureRegion... textureRegions) {
        setDecalAnimation(textureRegions);
        setPosition(startingPosition);
        currentStats = statistics;
        moveRate = new Vector3(0, 0, DungeonSection.getSize());
        direction = new Vector3(0, 0, 90);
        items = new ArrayList<>(getMaxItems());
    }

    private void setDecalAnimation(TextureRegion... textureRegions) {
        if (textureRegions == null) {
            throw new NullPointerException("TextureRegions can't be null");
        } else  {
            decalAnimation = new CharacterAnimations(new Array<>(textureRegions));
        }
    }

    public abstract int getMaxItems();

    public abstract int getMaxUsedItems();

    public void setPosition(DungeonSection section) {
        if (section != null) {
            ArrayList<Object> occupyingObjectCopy = new ArrayList<>(section.getOccupyingObjects());
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

            if (this.position != null) this.position.getOccupyingObjects().remove(this);
            this.position = section;
            section.getOccupyingObjects().add(this);
        }
    }

    public DungeonSection getPositionSection() {
        return position;
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
        decalAnimation.draw(batch, lookAt);
    }

    @Override
    public void update(float delta) {
        float yHeight = DungeonSection.getSize() / 2f;
        decalAnimation.setPosition(new Vector3(position.getPoint().x, yHeight, position.getPoint().y));
        decalAnimation.update(delta);
    }

    @Override
    public final void damage(int damageValue) {
        Statistics.Attribute armor = currentStats.get(Statistics.AttributeType.ARMOR);
        Statistics.Attribute health = currentStats.get(Statistics.AttributeType.CURRENT_HEALTH);
        int givenDamage = damageValue - armor.getValue();
        if (givenDamage > 0) {
            health.setValue(health.getValue() - givenDamage);
            Gdx.app.log(this.toString()+ " damaged, current health", Integer.toString(health.getValue()));
        }
        if (health.getValue() <= 0) die();
    }

    public void die() {
        for (Item item : getItems()) dropItem(item, position);
        Gdx.app.log("kill", this.toString());
    }

    @Override
    abstract public void processTurn();

    public DungeonSection moveToward() {
        DungeonSection position = getPositionSection();
        Vector2 possibleNextPosition = position.getPoint().cpy().add(getMoveRate().x, getMoveRate().z);
        DungeonSection possibleNextSection = position.getDungeon().getDungeonSectionAt(possibleNextPosition);
        if (possibleNextSection != null) {
            setPosition(possibleNextSection);
            return possibleNextSection;
        }
        return null;
    }

    public DungeonSection moveBack() {
        Vector2 possibleNextPosition = getPositionSection().getPoint().cpy().sub(getMoveRate().x, getMoveRate().z);
        DungeonSection possibleNextSection = getPositionSection().getDungeon().getDungeonSectionAt(possibleNextPosition);
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
    public void dropItem(Item item, DungeonSection position) {
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
