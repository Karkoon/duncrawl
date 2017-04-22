package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.GUI.UserInterface;
import com.karkoon.dungeoncrawler.Interfaces.Cacheable;
import com.karkoon.dungeoncrawler.Layers.CharacterLayer;
import com.karkoon.dungeoncrawler.Layers.ItemLayer;
import com.karkoon.dungeoncrawler.Layers.Layer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by @Karkoon on 2016-08-30.
 */
public class GameScreen implements Screen {

    private Graphics graphics;
    private ArrayList<Cacheable> cacheables = new ArrayList<>();
    private ArrayList<com.karkoon.dungeoncrawler.Interfaces.Drawable> drawables = new ArrayList<>();
    private ArrayList<Layer> layers = new ArrayList<>();
    private UserInterface userInterface;


    @Override
    public void show() {
        Dungeon dungeon = new HTTPDungeonProvider().getNewDungeon(new Random().nextInt(), 256, 32);
        CharacterLayer characterLayer = new CharacterLayer(dungeon);
        Character player = characterLayer.getMainCharacter();
        layers.add(characterLayer);
        layers.add(new ItemLayer(dungeon));
        cacheables.add(dungeon);
        drawables.addAll(layers);
        graphics = new Graphics(cacheables, drawables);
        userInterface = new UserInterface(player, graphics.getCamera());
        Gdx.input.setInputProcessor(userInterface.getInputProcessor()); // todo input multiplexer of userInterface and some sort of camera control.
    }

    @Override
    public void render(float delta) {
        for (Layer layer : layers) {
            layer.update(Gdx.graphics.getDeltaTime());
        }
        graphics.update();
        userInterface.step();
    }

    @Override
    public void resize(int width, int height) {
        graphics.resizeViewport(width, height);
        userInterface.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
