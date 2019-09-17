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
import de.brochantit.soerenkruck.Menu.MainMenu;
import de.brochantit.soerenkruck.TexturenIndex;

public class MapConfigurationMenu implements Screen {

    private SpriteBatch batch;

    private Stage stage;
    private BitmapFont buttonFont,
                        textFont,
                        HeadFont;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;
    TexturenIndex texturenIndex;

    String name;

    private Button xLowerButton;
    private Button xHigherButton;
    private Button yLowerButton;
    private Button yHigherButton;
    private Button timeHigherButton;
    private Button timeLowerButton;
    private Button createButton;
    private Button xEightHigherButton;
    private Button yEightHigherButton;
    private Button returnButton; //Todo: Button implementieren

    private int mapXSize = 1,
                mapYSize = 1,
                mapTime = 0;

    public MapConfigurationMenu (String name, TexturenIndex tx) {
        this.name = name;
        this.texturenIndex = tx;
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
        xEightHigherButton = new TextButton("+8", textButtonStyle);
        xEightHigherButton.setBounds(168, Gdx.graphics.getHeight()-68, 18, 18);
        yLowerButton = new TextButton("-", textButtonStyle);
        yLowerButton.setBounds(88, Gdx.graphics.getHeight()-91, 18, 18);
        yHigherButton = new TextButton("+", textButtonStyle);
        yHigherButton.setBounds(142, Gdx.graphics.getHeight()-91, 18, 18);
        yEightHigherButton = new TextButton("+8", textButtonStyle);
        yEightHigherButton.setBounds(168, Gdx.graphics.getHeight()-91, 18, 18);
        timeLowerButton = new TextButton("-", textButtonStyle);
        timeLowerButton.setBounds(88, Gdx.graphics.getHeight()-114, 18, 18);
        timeHigherButton = new TextButton("+", textButtonStyle);
        timeHigherButton.setBounds(242, Gdx.graphics.getHeight()-114, 18, 18);
        createButton = new TextButton("Erstellen", textButtonStyle);
        createButton.setBounds(Gdx.graphics.getWidth()-116, 16, 100, 32);
        returnButton = new TextButton("Hauptmenü", textButtonStyle);
        returnButton.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-36, 114, 28);

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
        xEightHigherButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapXSize += 8;
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
        yEightHigherButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapYSize += 8;
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
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MapEditor(mapXSize, mapYSize, mapTime, name, texturenIndex));
            }
        });
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name, texturenIndex));
            }
        });

        stage.addActor(xLowerButton);
        stage.addActor(xHigherButton);
        stage.addActor(xEightHigherButton);
        stage.addActor(yLowerButton);
        stage.addActor(yHigherButton);
        stage.addActor(yEightHigherButton);
        stage.addActor(timeLowerButton);
        stage.addActor(timeHigherButton);
        stage.addActor(createButton);
        stage.addActor(returnButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.35f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        HeadFont.draw(batch, "Neue Map erstellen.", 16, Gdx.graphics.getHeight()-18);

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
