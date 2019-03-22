package de.brochantit.soerenkruck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class JoinScreen implements Screen {

    SpriteBatch batch;

    BitmapFont font;

    Stage stage;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont buttonFont;
    Skin skin;
    TextureAtlas buttonAtlas;
    TextButton okButton;
    TextField textField;

    String name;

    final float ABS_16 = Gdx.graphics.getHeight()/67.5f;

    public JoinScreen(String name) {
        this.name = name;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        initMenu();
    }

    private void initMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonFont = new BitmapFont();
        buttonFont.setColor(0.1f, 0.1f, 0.1f, 1);
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/button/textButton.txt"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("unpressed");
        textButtonStyle.down = skin.getDrawable("pressed");

        okButton = new TextButton("Beitreten", textButtonStyle);
        okButton.setBounds(Gdx.graphics.getHeight() / 135, Gdx.graphics.getHeight() - (2 * (ABS_16)) - Gdx.graphics.getHeight() / 13.5f - 30, Gdx.graphics.getWidth() / 5, 30);



        stage.addActor(okButton);
        stage.addActor(textField);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.35f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Version 0.3.1 [21.03.2019] alpha build", 16, 24);
        batch.end();

        stage.act();
        stage.draw();
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

    }
}
