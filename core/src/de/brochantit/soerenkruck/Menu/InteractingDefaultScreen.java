package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.brochantit.soerenkruck.TexturenIndex;

public class InteractingDefaultScreen implements Screen {

    SpriteBatch defaultBatch;
    Stage defaultStage;
    Sprite defaultBackground;

    TexturenIndex defaultTexturenIndex;
    String defaultName;

    BitmapFont defaultFont;
    BitmapFont defaultBoldFont;
    BitmapFont defaultItalicFont;

    Skin defaultDarkButtonSkin;
    TextButton.TextButtonStyle defaultDarkTextButtonStyle;
    BitmapFont defaultDarkButtonFont;
    Skin defaultLightButtonSkin;
    TextButton.TextButtonStyle defaultLightTextButtonStyle;
    BitmapFont defaultLightButtonFont;

    InteractingDefaultScreen(TexturenIndex tx, String name) {
        this.defaultTexturenIndex = tx;
        this.defaultName = name;

        initActingElements();
    }

    @Override
    public void show() {
        defaultBackground = new Sprite(new Texture(Gdx.files.internal("textures/backgrounds/menu_bg_2.png")));
        defaultBackground.setSize(1920, 1080);
        defaultBackground.setOrigin(defaultBackground.getWidth()/2, defaultBackground.getHeight()/2);
        defaultBackground.setPosition(0, 0);
    }

    private void initActingElements() {
        defaultBatch = new SpriteBatch();
        defaultStage = new Stage();

        defaultFont = new BitmapFont(Gdx.files.internal("fonts/share-regular.fnt"));
        defaultBoldFont = new BitmapFont(Gdx.files.internal("fonts/share-bold.fnt"));
        defaultItalicFont = new BitmapFont(Gdx.files.internal("fonts/share-italic.fnt"));

        defaultStage = new Stage();
        Gdx.input.setInputProcessor(defaultStage);

        defaultDarkButtonSkin = new Skin();
        TextureAtlas darkButtonAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/menuButton_lite/textButton_lite.txt"));
        defaultDarkButtonSkin.addRegions(darkButtonAtlas);
        defaultDarkTextButtonStyle = new TextButton.TextButtonStyle();
        defaultDarkButtonFont = new BitmapFont();
        defaultDarkButtonFont.setColor(Color.WHITE);
        defaultDarkTextButtonStyle.font = defaultDarkButtonFont;
        defaultDarkTextButtonStyle.up = defaultDarkButtonSkin.getDrawable("pressed");
        defaultDarkTextButtonStyle.down = defaultDarkButtonSkin.getDrawable("unpressed");

        defaultLightButtonSkin = new Skin();
        TextureAtlas lightButtonAtlas = new TextureAtlas("textures/ui/button/textButton.txt");
        defaultLightButtonSkin.addRegions(lightButtonAtlas);
        defaultLightTextButtonStyle = new TextButton.TextButtonStyle();
        defaultLightButtonFont = new BitmapFont();
        defaultLightButtonFont.setColor(Color.BLACK);
        defaultLightTextButtonStyle.font = defaultLightButtonFont;
        defaultLightTextButtonStyle.up = defaultLightButtonSkin.getDrawable("unpressed");
        defaultLightTextButtonStyle.down = defaultLightButtonSkin.getDrawable("pressed");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        defaultBatch.begin();
        defaultBackground.draw(defaultBatch);
        defaultBatch.end();

        defaultStage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        defaultStage.dispose();
        defaultBatch.dispose();
    }
}
