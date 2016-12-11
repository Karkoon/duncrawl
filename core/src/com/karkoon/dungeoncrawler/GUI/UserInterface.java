package com.karkoon.dungeoncrawler.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

public class UserInterface {

    private Character player;
    private Camera camera;
    private CharacterInterfaceDialog inventory;
    private Stage stage;

    private float buttonSize = 50f;

    public UserInterface(Character player, Camera cameraToBeControlled) {
        this.stage = new Stage(new ScreenViewport(new OrthographicCamera()));
        this.player = player;
        this.camera = cameraToBeControlled;
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        generateFont(skin, 64, "default-font");
        stage.addActor(createControls(skin));
        inventory = new CharacterInterfaceDialog(player, skin, stage.getWidth(), stage.getHeight());
    }

    private Group createControls(Skin skin) {
        float xPosition = 10f;
        float yPosition = 10f;
        Group group = new Group();
        group.addActor(createButton(skin, xPosition, yPosition, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.rotateLeft();
            }
        }));

        xPosition += buttonSize;
        group.addActor(createButton(skin, xPosition, yPosition, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveBack();
                // todo introduce touch input, which changes viewVector of player
            }
        }));
        yPosition += buttonSize;
        group.addActor(createButton(skin, xPosition, yPosition, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveToward();
            }
        }));
        yPosition -= buttonSize;
        xPosition += buttonSize;
        group.addActor(createButton(skin, xPosition, yPosition, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.rotateRight();
            }
        }));
        xPosition += 2 * buttonSize;
        group.addActor(createButton(skin, xPosition, yPosition, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inventory.update();
                inventory.show(stage);
            }
        }));
        return group;
    }

    private Button createButton(Skin skin, float x, float y, EventListener listener) {
        Button button = new Button(skin);
        button.setPosition(x, y);
        button.setSize(buttonSize, buttonSize);
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

    public void step() {
        stage.act();
        camera.position.set(player.getDecal().getPosition().x, Dungeon.DungeonSection.SIZE * 0.75f, player.getDecal().getPosition().z);
        camera.direction.lerp(player.getDirection(), 0.1f);
        camera.update();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public InputProcessor getInputProcessor() {
        return stage;
    }
}
