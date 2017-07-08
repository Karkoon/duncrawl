package ashlified.loading;

import ashlified.DungeonCrawler;
import ashlified.GameScreen;
import ashlified.assetManager.SCMLDataAndResources;
import ashlified.assetManager.SCMLDataAndResourcesLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Mats Svensson
 */
public class LoadingScreen extends ScreenAdapter {

    private Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    private float startX, endX;
    private float percent;
    private Actor loadingBar;

    private Skin skin;
    private boolean isLoading = true;

    private DungeonCrawler game;

    public LoadingScreen(DungeonCrawler game) {
        this.game = game;
        this.game.assetManager.setLoader(SCMLDataAndResources.class, new SCMLDataAndResourcesLoader(new InternalFileHandleResolver()));

    }

    @Override
    public void show() {
        game.assetManager.load("loading/loading.pack", TextureAtlas.class);
        game.assetManager.finishLoading();
        stage = new Stage();
        createLoadingBarRelatedImages();
        loadAssets();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        screenBg.setSize(width, height);
        positionLoadingBar(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1 / 5f, 2 / 5f, 4 / 5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (game.assetManager.update() && !isLoading) {
            game.setScreen(new GameScreen(game.assetManager));
        }
        if (game.assetManager.update() && isLoading) createSkin();
        updateLoadingBar();
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        game.assetManager.unload("loading/loading.pack");
    }

    private void updateLoadingBar() {
        percent = Interpolation.linear.apply(percent, game.assetManager.getProgress(), 0.1f);
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();
    }

    private void positionLoadingBar(float width, float height) {
        logo.setX((width - logo.getWidth()) / 2);
        logo.setY((height - logo.getHeight()) / 2 + 100);
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        startX = loadingBarHidden.getX();
        endX = 440;
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    private void loadAssets() {
        game.assetManager.load("./npc/npcs.atlas", TextureAtlas.class);
        game.assetManager.load("npc/untitled.g3db", Model.class);
        game.assetManager.load("npc/backup.scml", SCMLDataAndResources.class);
    }

    private void createLoadingBarRelatedImages() {
        TextureAtlas atlas = game.assetManager.get("loading/loading.pack");
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));
        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
        anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        loadingBar = new LoadingBar(anim);
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);
    }

    private void createSkin() {
        skin = new Skin();
        int small = (int) stage.getHeight() / 30;
        int medium = (int) stage.getHeight() / 20;
        int big = (int) stage.getHeight() / 8;
        generateFont(big, "default-big");
        generateFont(medium, "default");
        generateFont(small, "default-small");
        skin.addRegions(new TextureAtlas(Gdx.files.internal("ui/skin.atlas")));
        skin.load(Gdx.files.internal("ui/skin.json"));
        isLoading = false;
    }

    private void generateFont(int size, String name) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/default2.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        BitmapFont fontData = generator.generateFont(params);
        generator.dispose();

        skin.add(name, fontData, BitmapFont.class);
    }
}