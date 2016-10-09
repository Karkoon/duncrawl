package com.karkoon.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.karkoon.dungeoncrawler.Characters.Character;
import com.karkoon.dungeoncrawler.GUI.UserInterface;
import com.karkoon.dungeoncrawler.Interfaces.Renderable;
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
    private ArrayList<com.karkoon.dungeoncrawler.Interfaces.Renderable> renderables = new ArrayList<Renderable>();
    private ArrayList<com.karkoon.dungeoncrawler.Interfaces.Drawable> drawables = new ArrayList<com.karkoon.dungeoncrawler.Interfaces.Drawable>();
    private ArrayList<Layer> layers = new ArrayList<Layer>();
    private UserInterface userInterface;

    @Override
    public void show() {
        Dungeon dungeon = DungeonRetriever.getNewDungeonWithHTTP(new Random().nextInt(), 64, 8);
        CharacterLayer characterLayer = new CharacterLayer(dungeon);
        Character player = characterLayer.getMainCharacter();
        layers.add(characterLayer);
        layers.add(new ItemLayer(dungeon));
        renderables.add(dungeon);
        drawables.addAll(layers);
        graphics = new Graphics(renderables, drawables);
        userInterface = new UserInterface(player, graphics.getCamera());
        Gdx.input.setInputProcessor(userInterface.getInputProcessor()); // todo input multiplexer of userInterface and some sort of camera control.
    }

    @Override
    public void render(float delta) {
        for (Layer layer : layers) {
            layer.update(Gdx.graphics.getDeltaTime());
        }
        graphics.step();
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
