package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.brochantit.soerenkruck.TexturenIndex;

public class InteractingDefaultScreen implements Screen {

    SpriteBatch defaultBatch;
    Stage defaultStage;

    TexturenIndex defaultTexturenIndex;
    String defaultName;

    BitmapFont defaultFont;

    Skin defaultDarkButtonSkin;
    TextButton.TextButtonStyle defaultDarkTextButtonStyle;

    InteractingDefaultScreen (TexturenIndex tx, String defaultName) {
        this.defaultTexturenIndex = tx;
        this.defaultName = defaultName;

        initActingElements();
    }

    @Override
    public void show() {

    }

    private void initActingElements() {
        defaultBatch = new SpriteBatch();
        defaultStage = new Stage();

        defaultStage = new Stage();
        Gdx.input.setInputProcessor(defaultStage);
        defaultFont = new BitmapFont();
        defaultFont.setColor(0.1f, 0.1f, 0.1f, 1);
        defaultDarkButtonSkin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/button/textButton.txt"));
        defaultDarkButtonSkin.addRegions(buttonAtlas);
        defaultDarkTextButtonStyle = new TextButton.TextButtonStyle();
        defaultDarkTextButtonStyle.font = defaultFont;
        defaultDarkTextButtonStyle.up = defaultDarkButtonSkin.getDrawable("unpressed");
        defaultDarkTextButtonStyle.down = defaultDarkButtonSkin.getDrawable("pressed");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
