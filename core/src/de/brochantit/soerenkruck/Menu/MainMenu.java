package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Const.PlayerSkin;
import de.brochantit.soerenkruck.Editor.MapConfigurationMenu;
import de.brochantit.soerenkruck.ServerGame;
import de.brochantit.soerenkruck.Texturen;

public class MainMenu implements Screen {

    SpriteBatch batch;
    BitmapFont font;

    Stage stage;
    TextButton.TextButtonStyle textButtonStyle, textButtonLiteStyle;
    BitmapFont buttonFont;
    Skin skin, skinLite;
    TextureAtlas buttonAtlas, buttonLiteAtlas;
    TextButton hostButton;
    TextButton characterButton;
    TextButton editorButton;
    TextButton beendenButton;

    Texturen texturen;
    Texture player;
    Sprite background;

    Music music;

    String name;

    final float ABS_16 = Gdx.graphics.getHeight()/67.5f;
    private int currentCharacter = 1;

    public MainMenu (String name) {
        this.name = name;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        texturen = new Texturen();

        FileHandle settingsFile = Gdx.files.local("config/settings.afd");
        if (settingsFile.exists()) {
            String tmp = settingsFile.readString().replace("\\r?\\n", "");
            String ready[] = tmp.split(";");
            currentCharacter = Integer.valueOf(ready[0]);
        } else {
            currentCharacter = 0;
        }
        if (currentCharacter == PlayerSkin.PLAYER_STANDARD) {
            player = texturen.texture(Texturen.PLAYER_STANDARD_RIGHT);
        } else if (currentCharacter == PlayerSkin.PLAYER_NINJEO) {
            player = texturen.texture(Texturen.PLAYER_NINJEO_RIGHT);
        } else if (currentCharacter == PlayerSkin.PLAYER_GENJI) {
            player = texturen.texture(Texturen.PLAYER_GENJI_RIGHT);
        }

        background = new Sprite(new Texture(Gdx.files.internal("textures/backgrounds/tmp_bg.jpg")));
        background.setSize(1920, 1080);
        background.setOrigin(background.getWidth()/2, background.getHeight()/2);
        background.setPosition(0, 0);

        initMenu();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/titelmusik.mp3"));
        music.setLooping(true);
        music.play();
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

        skinLite = new Skin();
        buttonLiteAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/menuButton_lite/textButton_lite.txt"));
        skinLite.addRegions(buttonLiteAtlas);
        textButtonLiteStyle = new TextButton.TextButtonStyle();
        textButtonLiteStyle.font = buttonFont;
        textButtonLiteStyle.up = skinLite.getDrawable("pressed");
        textButtonLiteStyle.down = skinLite.getDrawable("unpressed");

        hostButton = new TextButton("Spielen", textButtonLiteStyle);
        hostButton.setBounds(Gdx.graphics.getHeight()/135, Gdx.graphics.getHeight()-(2*(ABS_16))-Gdx.graphics.getHeight()/13.5f-30, Gdx.graphics.getWidth()/5, 30);
        characterButton = new TextButton("Charakter", textButtonLiteStyle);
        characterButton.setBounds(Gdx.graphics.getHeight()/135, Gdx.graphics.getHeight()-(3*(ABS_16))-Gdx.graphics.getHeight()/13.5f-60, Gdx.graphics.getWidth()/5, 30);
        editorButton = new TextButton("Editor", textButtonLiteStyle);
        editorButton.setBounds(Gdx.graphics.getHeight()/135, Gdx.graphics.getHeight()-(4*(ABS_16))-Gdx.graphics.getHeight()/13.5f-90, Gdx.graphics.getWidth()/5, 30);
        beendenButton = new TextButton("Beenden", textButtonStyle);
        beendenButton.setBounds(Gdx.graphics.getWidth()-136, 8,128, 48);

        hostButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileHandle cfgFile = Gdx.files.local("config/network.afd");
                String cfg[] = cfgFile.readString().split(";");
                music.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerGame(name, false));
            }
        });
        editorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MapConfigurationMenu(name));
            }
        });
        characterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new CharacterChooser(texturen, name));
            }
        });
        beendenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.stop();
                System.exit(2);
            }
        });

        stage.addActor(hostButton);
        stage.addActor(characterButton);
        stage.addActor(editorButton);
        stage.addActor(beendenButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.35f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        if (currentCharacter == PlayerSkin.PLAYER_STANDARD) {
            batch.draw(player, Gdx.graphics.getWidth() / 240 + Gdx.graphics.getHeight() / 54, Gdx.graphics.getHeight() - (ABS_16) - Gdx.graphics.getHeight() / 13.5f, Gdx.graphics.getHeight() / 27, Gdx.graphics.getHeight() / 13.5f);
        } else {
            batch.draw(player, Gdx.graphics.getWidth() / 240, Gdx.graphics.getHeight() - (ABS_16) - Gdx.graphics.getHeight() / 13.5f, Gdx.graphics.getHeight() / 13.5f, Gdx.graphics.getHeight() / 13.5f);
        }
        font.draw(batch, "Version 0.3.1 [21.03.2019] alpha build", 16, 24);
        font.draw(batch, "Willkommen, " + name, Gdx.graphics.getWidth()/240 + Gdx.graphics.getHeight()/13.5f + ABS_16, Gdx.graphics.getHeight() - ABS_16 - 8);
        batch.end();

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
        music.dispose();
        stage.dispose();
        font.dispose();
        batch.dispose();
        buttonFont.dispose();
    }
}