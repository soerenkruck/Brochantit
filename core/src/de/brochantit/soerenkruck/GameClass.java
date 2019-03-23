package de.brochantit.soerenkruck;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Const.MapData;
import de.brochantit.soerenkruck.Menu.MainMenu;
import de.brochantit.soerenkruck.Server.Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GameClass implements Screen {

    private String name, ip;
    private boolean isServer;
    private Socket socket;

    private SpriteBatch batch;
    private Stage stage;

    private ArrayList<TileInformation> mapInformation;
    private MapData mapData;
    private Server server;

    private OrthographicCamera cam;
    private Player player;
    private int skin = 0;

    private TexturenIndex texturenIndex;

    private BitmapFont font;
    private String txt = "";

    public GameClass(String name, boolean isServer) {
        this.name = name;
        try {
            this.ip = String.valueOf(Inet4Address.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("HostExcpetion");
        }
        this.isServer = isServer;
    }

    @Override
    public void show () {
        batch = new SpriteBatch();
        texturenIndex = new TexturenIndex();

        FileHandle settingsFile = Gdx.files.local("config/settings.afd");
        String tmp = settingsFile.readString().replace("\\r?\\n", "");
        String tmpLinesofSt[] = tmp.split(";");
        skin = Integer.valueOf(tmpLinesofSt[0]);

        FileHandle handle = Gdx.files.local("maps/tutorial/map.afm");
        String data = handle.readString();
        String lines[] = data.split(";");
        FileHandle mdfile = Gdx.files.local("maps/tutorial/map_data.afmd");
        String mapdat = mdfile.readString().replace("\\r?\\n", "");
        String t[] = mapdat.split(";");

        mapInformation = new ArrayList<TileInformation>();
        mapData = new MapData(Integer.valueOf(t[0].replace("X=", "")), Integer.valueOf(t[1].replace("Y=", "")), Integer.valueOf((t[2]).replace("TIME=", "")));

        for (int x = 0; x < mapData.MAP_X; x++) {
            for (int y = 0; y < mapData.MAP_Y; y++) {
                int place = y + (x*(mapData.MAP_Y));
                String fieldData[] = lines[place].split(",");
                mapInformation.add(new TileInformation(Integer.valueOf(fieldData[0].replace("\r\n", "")), Boolean.valueOf(fieldData[1]), texturenIndex, x, y));
            }
        }

        player = new Player(0, 0, skin, name, texturenIndex);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(player.playerRec.x, player.playerRec.y, 0);
        cam.update();

        font = new BitmapFont();

        if (isServer)
            server = new Server(1339, texturenIndex);

        player = new Player(0, 0, Integer.valueOf(tmpLinesofSt[0]), this.name, texturenIndex);

        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 2000;

        //socket = Gdx.net.newClientSocket(Net.Protocol.TCP, this.ip, 1338, socketHints);

        //try {
        //    socket.getOutputStream().write(("connect="+name+";"+this.ip+"\n").getBytes());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        initUI();
    }

    private void initUI() {
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont buttonFont;
        Skin skin;
        TextureAtlas buttonAtlas;
        TextButton exitButton;

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

        exitButton = new TextButton("Beenden", textButtonStyle);
        exitButton.setBounds(Gdx.graphics.getWidth()-108, Gdx.graphics.getHeight()-32, 100, 24);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name, texturenIndex));
            }
        });

        stage.addActor(exitButton);
    }

    @Override
    public void render (float delta) {
        if (mapData.TIME == MapData.TIME_NIGHT) {
            Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        } else if (mapData.TIME == MapData.TIME_EVENING) {
            Gdx.gl.glClearColor(1, 0.529f, 0.1f, 1);
        } else if (mapData.TIME == MapData.TIME_DAY) {
            Gdx.gl.glClearColor(0.5f, 0.65f, 1f, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);

        InputHandling();
        cam.update();
        player.update(); //TODO:

        batch.begin();

        for (int ix = 0; ix < mapData.MAP_X; ix ++) {
            for (int iy = 0; iy < mapData.MAP_Y; iy++) {
                batch.draw(texturenIndex.texture(TexturenIndex.GROUND_STANDARD), ix*MapData.MAP_TILE_SIZE, iy*MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE);
            }
        }

        for (int x = 0; x < mapData.MAP_X; x++) {
            for (int y = 0; y < mapData.MAP_Y; y++) {
                int place = y + (x*mapData.MAP_Y);
                mapInformation.get(place).sprite.draw(batch);
            }
        }

        player.playerSprite.draw(batch);

        font.draw(batch, txt, 0, 0);

        if (isServer) {
            try {
                font.draw(batch, name + "@" + String.valueOf(Inet4Address.getLocalHost()), -0, -50);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
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

    private void InputHandling() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (player.playerRec.getY() + player.playerRec.height < mapData.MAP_Y*MapData.MAP_TILE_SIZE) {
                player.move(0, 4, true);
                for (int i = 0; i < mapInformation.size()-1; i++) {
                    if (player.feetRectangle.overlaps(mapInformation.get(i).rec) && mapInformation.get(i).isStatic) {
                        player.move(0, -4, false);
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (player.playerRec.x > 0) {
                player.move(-4, 0, true);
                for (int i = 0; i < mapInformation.size()-1; i++) {
                    if (player.feetRectangle.overlaps(mapInformation.get(i).rec) && mapInformation.get(i).isStatic) {
                        player.move(4, 0, false);
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (player.playerRec.y > 0) {
                player.move(0, -4, true);
                for (int i = 0; i < mapInformation.size()-1; i++) {
                    if (player.feetRectangle.overlaps(mapInformation.get(i).rec) && mapInformation.get(i).isStatic) {
                        player.move(0, 4, false);
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (player.playerRec.x + player.playerRec.width < mapData.MAP_X*MapData.MAP_TILE_SIZE)
                player.move(4, 0, true);
            for (int i = 0; i < mapInformation.size()-1; i++) {
                if (player.feetRectangle.overlaps(mapInformation.get(i).rec) && mapInformation.get(i).isStatic) {
                    player.move(-4, 0, false);
                }
            }
        }

        if (player.playerRec.getY() > cam.position.y + 40) {
            cam.translate(0, 4);
        }
        if (player.playerRec.getX() < cam.position.x - (40 + MapData.MAP_TILE_SIZE)) {
            cam.translate(-4, 0);
        }
        if (player.playerRec.getY() < cam.position.y - (40 + MapData.MAP_TILE_SIZE)) {
            cam.translate(0, -4);
        }
        if (player.playerRec.getX() > cam.position.x + 40) {
            cam.translate(4, 0);
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        stage.dispose();
        font.dispose();
    }
}