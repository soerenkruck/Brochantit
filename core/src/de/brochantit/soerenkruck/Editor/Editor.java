package de.brochantit.soerenkruck.Editor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import de.brochantit.soerenkruck.*;
import de.brochantit.soerenkruck.Const.GroundTypes;
import de.brochantit.soerenkruck.Const.MapData;
import de.brochantit.soerenkruck.Menu.MainMenu;

import java.util.ArrayList;

public class Editor implements Screen {

    SpriteBatch batch;
    OrthographicCamera cam;
    BitmapFont normalText;
    private boolean go = true;
    private final float CAM_SPEED = 5;

    ArrayList<TileInformation> mapInformation;
    private MapData mapData;
    private int act = 0, max = 0;

    private int posX = 0, posY = 0, place;
    private int CurrentType = GroundTypes.EMPTY;
    private boolean Static = false;

    Texturen texturen;

    Stage stage;
    String name;

    public Editor(int x, int y, int time, String name) {
        this.name = name;

        mapData = new MapData(x, y, time);
        mapInformation = new ArrayList<TileInformation>();

        texturen = new Texturen();

        for (int ix = 0; ix < mapData.MAP_X; ix++) {
            for (int iy = 0; iy < mapData.MAP_Y; iy++) {
                mapInformation.add(new TileInformation(GroundTypes.EMPTY, false, texturen, ix, iy));
            }
        }

        if ((mapInformation.size()) == (mapData.MAP_X*mapData.MAP_Y)) {
            System.out.println("Map wurde vollständig erstellt.");
        } else {
            System.err.println("Map wurde fehlerhaft erstellt. [" + (mapInformation.size()-1) + " | " + (mapData.MAP_X*mapData.MAP_Y) + "]");
        }

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 0);
        cam.update();

        normalText = new BitmapFont();

        initUI();

        max = mapInformation.size()+1;
        act = 0;
    }

    private void initUI() {
        BitmapFont buttonFont;
        Skin skin;
        TextureAtlas buttonAtlas;
        TextButton.TextButtonStyle ChangeButtonStyle, checkedButtonStyle;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonFont = new BitmapFont();
        buttonFont.setColor(0.1f, 0.1f, 0.1f, 1);
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("textures/ui/button/textButton.txt"));
        skin.addRegions(buttonAtlas);
        ChangeButtonStyle = new TextButton.TextButtonStyle();
        ChangeButtonStyle.font = buttonFont;
        ChangeButtonStyle.up = skin.getDrawable("unpressed");
        ChangeButtonStyle.down = skin.getDrawable("pressed");
        checkedButtonStyle = new TextButton.TextButtonStyle();
        checkedButtonStyle.font = buttonFont;
        checkedButtonStyle.up = skin.getDrawable("unpressed");
        checkedButtonStyle.checked = skin.getDrawable("pressed");

        TextButton PlaceButton,
                    saveButton,
                    homeButton;
        final TextButton  nullCheckedButton,
                    grassCheckedButton,
                    wallCheckedButton,
                    staticCheckedButton;

        PlaceButton = new TextButton("Platzieren", ChangeButtonStyle);
        PlaceButton.setBounds(8, Gdx.graphics.getHeight()-32, 100, 24);
        saveButton = new TextButton("Speichern", ChangeButtonStyle);
        saveButton.setBounds(Gdx.graphics.getWidth()-116, Gdx.graphics.getHeight()-32, 108, 24);
        nullCheckedButton = new TextButton("null", ChangeButtonStyle);
        nullCheckedButton.setBounds(8, Gdx.graphics.getHeight()-64, 64, 20);
        grassCheckedButton = new TextButton("Gras", ChangeButtonStyle);
        grassCheckedButton.setBounds(8, Gdx.graphics.getHeight()-88, 64, 20);
        wallCheckedButton = new TextButton("Wand", ChangeButtonStyle);
        wallCheckedButton.setBounds(8, Gdx.graphics.getHeight()-112, 64, 20);
        staticCheckedButton = new TextButton("statisch", checkedButtonStyle);
        staticCheckedButton.setBounds(8, Gdx.graphics.getHeight()-142, 64, 20);
        homeButton = new TextButton("Hauptbildschirm", ChangeButtonStyle);
        homeButton.setBounds(Gdx.graphics.getWidth()-238, Gdx.graphics.getHeight()-32, 114, 24);

        nullCheckedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CurrentType = GroundTypes.EMPTY;
            }
        });
        grassCheckedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CurrentType = GroundTypes.GRASS;
            }
        });
        wallCheckedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CurrentType = GroundTypes.WALL;
            }
        });
        staticCheckedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Static = !Static;
            }
        });
        PlaceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapInformation.get(place).type = CurrentType;
                mapInformation.get(place).isStatic = Static;
                mapInformation.get(place).update();
            }
        });
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                save();
            }
        });
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name));
            }
        });

        stage.addActor(PlaceButton);
        stage.addActor(nullCheckedButton);
        stage.addActor(grassCheckedButton);
        stage.addActor(wallCheckedButton);
        stage.addActor(staticCheckedButton);
        stage.addActor(saveButton);
        stage.addActor(homeButton);
    }

    private void save() {

        act = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {

                FileHandle mapDataFile = Gdx.files.local("maps/map_data.afmd");
                FileHandle mapFile = Gdx.files.local("maps/map.afm");

                if (mapDataFile.exists())
                    mapDataFile.delete();

                mapDataFile.writeString("X=" + mapData.MAP_X + ";Y=" + mapData.MAP_Y + ";" + "TIME=" + mapData.TIME, true);

                act++;

                if (mapFile.exists())
                    mapFile.delete();

                for (int i = 0; i < mapInformation.size(); i++) {
                    mapFile.writeString(mapInformation.get(i).type + "," + mapInformation.get(i).isStatic + ";", true);
                    act++;
                }
            }
        }).start();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (mapData.TIME == MapData.TIME_NIGHT) {
            Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        } else if (mapData.TIME == MapData.TIME_EVENING) {
            Gdx.gl.glClearColor(1, 0.529f, 0.1f, 1);
        } else if (mapData.TIME == MapData.TIME_DAY) {
            Gdx.gl.glClearColor(0.5f, 0.65f, 1f, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        
        inputHandling();
        place = posY + (posX*mapData.MAP_Y);

        batch.begin();
        for (int ix = 0; ix < mapData.MAP_X; ix ++) {
            for (int iy = 0; iy < mapData.MAP_Y; iy++) {
                batch.draw(texturen.texture(Texturen.GROUND_STANDARD), ix*MapData.MAP_TILE_SIZE, iy*MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE);
            }
        }

        for (int x = 0; x < mapData.MAP_X; x++) {
            for (int y = 0; y < mapData.MAP_Y; y++) {
                int place = y + (x*mapData.MAP_Y);
                mapInformation.get(place).sprite.draw(batch);
            }
        }

        batch.draw(texturen.texture(Texturen.EDITOR_SELECT), mapInformation.get(place).rec.x, mapInformation.get(place).rec.y, mapInformation.get(place).rec.width, mapInformation.get(place).rec.height);

        normalText.draw(batch, "Aktuelle Position: X=" + posX + "; Y=" + posY + "; Array[i -> place]=" + place + "; CAM ZOOM=" + cam.zoom + ";", 0, -8);
        normalText.draw(batch, "Aktuell ausgewählter Typ: " + GroundTypes.GroundTypeString[CurrentType] + "; IstStatisch= " + Static, 0, - 24);
        int percentageOfSaving = Math.round(act/max * 100);
        normalText.draw(batch, "Speichern: " + percentageOfSaving + "%", 0, - 42);

        batch.end();

        stage.draw();
    }

    private void inputHandling() {
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            cam.translate(0, CAM_SPEED);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            cam.translate(-CAM_SPEED, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            cam.translate(0, -CAM_SPEED);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            cam.translate(CAM_SPEED, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            if (cam.zoom > 0.08)
                cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            cam.zoom = 1;
        }
        cam.update();

        if (go) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (posY < mapData.MAP_Y - 1) {
                    posY++;
                    go = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(125);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            go = true;
                        }
                    }).start();
                }
            }
        }
        if (go) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (posX > 0) {
                    posX--;
                    go = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(125);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            go = true;
                        }
                    }).start();
                }
            }
        }
        if (go) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (posY > 0) {
                    posY--;
                    go = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(125);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            go = true;
                        }
                    }).start();
                }
            }
        }
        if (go) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (posX < mapData.MAP_X - 1) {
                    posX++;
                    go = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(125);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            go = true;
                        }
                    }).start();
                }
            }
        }
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
        normalText.dispose();
    }
}
