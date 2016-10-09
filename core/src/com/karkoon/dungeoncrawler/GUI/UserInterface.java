package com.karkoon.dungeoncrawler.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.Dungeon;


/**
 * Created by @Karkoon on 2016-08-29.
 */

public class UserInterface extends Stage {

    private Character player;
    private Camera camera;
    private CharacterInsterfaceDialog inventory;

    public UserInterface(Character player, Camera camera) {
        super(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        this.player = player;
        this.camera = camera;
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        generateFont(skin, 32, "default-font");
        this.addActor(createControls(skin));
        inventory = new CharacterInsterfaceDialog(player, skin);
    }

    private Group createControls(Skin skin) {
        Group group = new Group();
        group.addActor(createButton(skin, 10, 10, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.rotateLeft();
            }
        }));
        group.addActor(createButton(skin, 110, 10, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveBack();
                // todo introduce touch input, which changes viewVector of player
            }
        }));
        group.addActor(createButton(skin, 210, 10, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.rotateRight();
            }
        }));
        group.addActor(createButton(skin, 110, 110, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveToward();
            }
        }));
        group.addActor(createButton(skin, 400, 10, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inventory.update();
                inventory.show(UserInterface.this);
            }
        }));
        return group;
    }

    private Button createButton(Skin skin, float x, float y, EventListener listener) {
        Button button = new Button(skin);
        button.setPosition(x, y);
        button.setSize(100, 100);
        button.addListener(listener);
        return button;
    }

    private void generateFont(Skin skin, int size, String name) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default2.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        BitmapFont fontData = generator.generateFont(params);
        generator.dispose();
        skin.add(name, fontData, BitmapFont.class);
    }

    @Override
    public void act() {
        super.act();
        camera.position.set(player.getDecal().getPosition().x, Dungeon.DungeonSection.WIDTH * 0.75f, player.getDecal().getPosition().z);
        camera.direction.lerp(player.getDirection(), 0.1f);
        camera.update();
    }
}
