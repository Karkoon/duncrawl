package ashlified.loading;

import ashlified.AssetPaths;
import ashlified.DungeonCrawler;
import ashlified.GameScreen;
import ashlified.graphics.LevelThemesRandomizer;
import ashlified.loading.assetmanagerloaders.LevelThemesRandomizerLoader;
import ashlified.loading.assetmanagerloaders.NpcBlueprintListLoader;
import ashlified.loading.assetmanagerloaders.ScmlDataWithResourcesLoader;
import ashlified.loading.assetmanagerloaders.StringLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
 * https://github.com/Matsemann/libgdx-loading-screen
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
    private AssetManager assetManager;

    public LoadingScreen(DungeonCrawler game) {
        this.game = game;
        assetManager = game.assetManager;
        setCustomLoaders();
    }

    private void setCustomLoaders() {
        assetManager.setLoader(ScmlDataWithResourcesLoader.SCMLDataWithResources.class, new ScmlDataWithResourcesLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(LevelThemesRandomizer.class, new LevelThemesRandomizerLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(NpcBlueprintListLoader.EnemyNPCBlueprintList.class, new NpcBlueprintListLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(String.class, new StringLoader(new InternalFileHandleResolver()));
    }

    private void loadAssets() {
        assetManager.load(AssetPaths.NPC_ATLAS, TextureAtlas.class);
        assetManager.load(AssetPaths.NPC_MODEL, Model.class);
        assetManager.load(AssetPaths.SCML_FILE, ScmlDataWithResourcesLoader.SCMLDataWithResources.class);
        assetManager.load(AssetPaths.LEVEL_THEMES_DIR, LevelThemesRandomizer.class);
        assetManager.load(AssetPaths.NPC_DIRECTORY, NpcBlueprintListLoader.EnemyNPCBlueprintList.class);
        assetManager.load(AssetPaths.WALL_TEXTURE, Texture.class);
        assetManager.load(AssetPaths.VERTEX_SHADER, String.class);
        assetManager.load(AssetPaths.FRAGMENT_SHADER, String.class);
        assetManager.load(AssetPaths.CHEST_MODEL, Model.class);
        assetManager.load(AssetPaths.SKIN, Skin.class);
        assetManager.load(AssetPaths.FLOOR_TEXTURE, Texture.class);
    }

    @Override
    public void show() {
        assetManager.load("loading/loading.pack", TextureAtlas.class);
        assetManager.finishLoading();
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
        clearScreen();
        //if (assetManager.update() && !isLoading) {
        if (assetManager.update()) {
            game.setScreen(new GameScreen(assetManager));
        }
        //if (assetManager.update() && isLoading) createSkin();
        updateLoadingBar();
        stage.act();
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void hide() {
        assetManager.unload("loading/loading.pack");
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