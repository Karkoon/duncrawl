package com.karkoon.dungeoncrawler.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Dungeon;
import com.karkoon.dungeoncrawler.Interfaces.Drawable;
import com.karkoon.dungeoncrawler.Statistics;

/**
 * Created by Roksana on 03.09.2016.
 */
public abstract class Item implements Drawable {

    public boolean isDropped = false;
    private String name;
    private String description;
    private Dungeon.DungeonSection position;
    private Decal decal;
    private Statistics stats;
    private float height = Dungeon.DungeonSection.HEIGHT / 2f;
    private float speed = 0.2f;
    private float originalHeight = height;

    protected Item(String name, String description, Statistics stats, FileHandle fileHandle) {
        this.name = name;
        this.description = description;
        TextureRegion textureRegion = new TextureRegion(new Texture(fileHandle));
        this.decal = Decal.newDecal(textureRegion.getRegionWidth() / 20f, textureRegion.getRegionHeight() / 20f, textureRegion, true);
        this.stats = stats;
    }

    @Override
    public void draw(DecalBatch batch, Vector3 lookAt) {
        if (isDropped) {
            decal.lookAt(lookAt, Vector3.Y);
            batch.add(decal);
            height = speed * Gdx.graphics.getDeltaTime() + height;
            if (height > 1.25f * originalHeight || height < originalHeight / 1.25f) speed = -speed;
            decal.setPosition(position.point.x, height, position.point.y);
        }
    }

    public boolean pickUp(Character character) {
        if (character.putItem(this)) {
            position.occupyingObject.remove(this);
            isDropped = false;
            return true;
        }
        return false;
    }

    public Item drop(Dungeon.DungeonSection section) {
        position = section;
        section.occupyingObject.add(this);
        decal.setPosition(section.point.x, height, section.point.y);
        isDropped = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Statistics getStats() {
        return stats;
    }

    public Decal getDecal() {
        return decal;
    }
}
