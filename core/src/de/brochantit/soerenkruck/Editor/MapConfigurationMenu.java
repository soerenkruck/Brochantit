package de.brochantit.soerenkruck.Editor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Const.MapData;

public class MapConfigurationMenu implements Screen {

    private SpriteBatch batch;

    private Stage stage;
    private BitmapFont buttonFont,
                        textFont,
                        HeadFont;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;

    String name;

    private Button xLowerButton;
    private Button xHigherButton;
    private Button yLowerButton;
    private Button yHigherButton;
    private Button timeHigherButton;
    private Button timeLowerButton;
    private Button createButton;

    private int mapXSize = 1,
                mapYSize = 1,
                mapTime = 0;

    public MapConfigurationMenu (String name) {
        this.name = name;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();

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

        textFont = new BitmapFont();
        textFont.setColor(1, 1, 1,1);
        HeadFont = new BitmapFont();
        HeadFont.setColor(1, 0.8f, 0.5f, 1);

        xLowerButton = new TextButton("-", textButtonStyle);
        xLowerButton.setBounds(88, Gdx.graphics.getHeight()-68, 18, 18);
        xHigherButton = new TextButton("+", textButtonStyle);
        xHigherButton.setBounds(142, Gdx.graphics.getHeight()-68, 18, 18);
        yLowerButton = new TextButton("-", textButtonStyle);
        yLowerButton.setBounds(88, Gdx.graphics.getHeight()-91, 18, 18);
        yHigherButton = new TextButton("+", textButtonStyle);
        yHigherButton.setBounds(142, Gdx.graphics.getHeight()-91, 18, 18);
        timeLowerButton = new TextButton("-", textButtonStyle);
        timeLowerButton.setBounds(88, Gdx.graphics.getHeight()-114, 18, 18);
        timeHigherButton = new TextButton("+", textButtonStyle);
        timeHigherButton.setBounds(242, Gdx.graphics.getHeight()-114, 18, 18);
        createButton = new TextButton("Erstellen", textButtonStyle);
        createButton.setBounds(Gdx.graphics.getWidth()-116, 16, 100, 32);

        xLowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mapXSize > 1) {
                    mapXSize--;
                }
            }
        });
        xHigherButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapXSize++;
            }
        });
        yLowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mapYSize > 1) {
                    mapYSize--;
                }
            }
        });
        yHigherButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapYSize++;
            }
        });
        timeLowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mapTime > 0)
                    mapTime--;
            }
        });
        timeHigherButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mapTime < MapData.TIME_SIZE-1)
                    mapTime++;
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Editor(mapXSize, mapYSize, mapTime, name));
            }
        });

        stage.addActor(xLowerButton);
        stage.addActor(xHigherButton);
        stage.addActor(yLowerButton);
        stage.addActor(yHigherButton);
        stage.addActor(timeLowerButton);
        stage.addActor(timeHigherButton);
        stage.addActor(createButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.35f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        HeadFont.draw(batch, "Map | Konfigurationsmenü", 16, Gdx.graphics.getHeight()-18);

        textFont.draw(batch, "X Größe ", 16, Gdx.graphics.getHeight()-52);
        textFont.draw(batch, "Y Größe ", 16, Gdx.graphics.getHeight()-76);
        textFont.draw(batch, "Zeit", 16, Gdx.graphics.getHeight()-100);

        textFont.draw(batch, "" + mapXSize, 112, Gdx.graphics.getHeight()-52);
        textFont.draw(batch, "" + mapYSize, 112, Gdx.graphics.getHeight()-76);
        textFont.draw(batch, "" + MapData.timeStrings[mapTime], 112, Gdx.graphics.getHeight()-100);
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
        HeadFont.dispose();
        textFont.dispose();
        batch.dispose();
        stage.dispose();
        buttonFont.dispose();
    }
}