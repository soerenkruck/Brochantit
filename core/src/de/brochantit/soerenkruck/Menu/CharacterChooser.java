package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.brochantit.soerenkruck.Const.PlayerSkin;
import de.brochantit.soerenkruck.Texturen;

public class CharacterChooser implements Screen {

    private int currentCharacter = 1;
    SpriteBatch batch;
    Stage stage;
    Texturen texturen;

    BitmapFont HeadFont;
    String name;

    public CharacterChooser(Texturen tx, String name) {
        this.texturen = tx;
        this.name = name;

        FileHandle settingsFile = Gdx.files.local("config/settings.afd");
        if (settingsFile.exists()) {
            String tmp = settingsFile.readString().replace("\\r?\\n", "");
            String ready[] = tmp.split(";");
            currentCharacter = Integer.valueOf(ready[0]);
        } else {
            currentCharacter = 0;
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        HeadFont = new BitmapFont();
        HeadFont.setColor(1, 0.8f, 0.5f, 1);

        initUI();
    }

    private void initUI() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont buttonFont = new BitmapFont();
        buttonFont.setColor(0.1f, 0.1f, 0.1f, 1);
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/button/textButton.txt"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("unpressed");
        textButtonStyle.down = skin.getDrawable("pressed");

        TextButton standardButton = new TextButton("Standard", textButtonStyle);
        standardButton.setBounds(88, Gdx.graphics.getHeight() - 84, 128, 32);
        TextButton ninjeoButton = new TextButton("Ninjeo", textButtonStyle);
        ninjeoButton.setBounds(88, Gdx.graphics.getHeight()-160, 128, 32);
        TextButton genjiButton = new TextButton("Genji", textButtonStyle);
        genjiButton.setBounds(88, Gdx.graphics.getHeight() - 236, 128, 32);
        TextButton saveButton = new TextButton("Speichern", textButtonStyle);
        saveButton.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-32, 114, 24);

        standardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentCharacter = PlayerSkin.PLAYER_STANDARD;
            }
        });
        ninjeoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentCharacter = PlayerSkin.PLAYER_NINJEO;
            }
        });
        genjiButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentCharacter = PlayerSkin.PLAYER_GENJI;
            }
        });
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileHandle newSetFile = Gdx.files.local("config/settings.afd");
                        if (newSetFile.exists())
                            newSetFile.delete();

                        newSetFile.writeString(currentCharacter + ";", true);
                    }
                }).start();

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name));
            }
        });

        stage.addActor(standardButton);
        stage.addActor(ninjeoButton);
        stage.addActor(genjiButton);
        stage.addActor(saveButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.35f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        HeadFont.draw(batch, "Character | Auswahl", 16, Gdx.graphics.getHeight()-18);

        batch.draw(texturen.texture(Texturen.PLAYER_STANDARD_RIGHT), 24, Gdx.graphics.getHeight()-100, 32, 64);
        batch.draw(texturen.texture(Texturen.PLAYER_NINJEO_RIGHT), 8, Gdx.graphics.getHeight()-176, 64, 64);
        batch.draw(texturen.texture(Texturen.PLAYER_GENJI_RIGHT), 8, Gdx.graphics.getHeight()-252, 64, 64);

        if (currentCharacter == PlayerSkin.PLAYER_STANDARD) {
            batch.draw(texturen.texture(Texturen.PLAYER_STANDARD_LEFT), Gdx.graphics.getWidth()/2+128, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/7*6)/2, Gdx.graphics.getHeight()/7*6/2, Gdx.graphics.getHeight()/7*6);
        } else if (currentCharacter == PlayerSkin.PLAYER_NINJEO) {
            batch.draw(texturen.texture(Texturen.PLAYER_NINJEO_LEFT), Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2)/7*6)/2, (Gdx.graphics.getWidth()/2)/7*6, (Gdx.graphics.getWidth()/2)/7*6);
        } else if (currentCharacter == PlayerSkin.PLAYER_GENJI) {
            batch.draw(texturen.texture(Texturen.PLAYER_GENJI_LEFT), Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2)/7*6)/2, (Gdx.graphics.getWidth()/2)/7*6, (Gdx.graphics.getWidth()/2)/7*6);
        }
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
        batch.dispose();
        stage.dispose();
        HeadFont.dispose();
    }
}
