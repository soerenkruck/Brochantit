package de.brochantit.soerenkruck.Game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Const.MapData;
import de.brochantit.soerenkruck.Menu.MainMenu;
import de.brochantit.soerenkruck.Player;
import de.brochantit.soerenkruck.ScreenElements.BoldText;
import de.brochantit.soerenkruck.Server.Server;
import de.brochantit.soerenkruck.TexturenIndex;
import de.brochantit.soerenkruck.TileInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;

public class GameClass implements Screen { // TODO: Server Implementieren.

    // Client-Server Konfiguration
    private String name, playerIP, serverPort, serverIP;
    private Socket client;
    private int clientID;
    private Thread clientSend;
    private boolean isClientActive = true;

    private SpriteBatch batch;
    private Stage stage;

    private ArrayList<TileInformation> mapInformation;
    private MapData mapData;

    private OrthographicCamera cam;
    private Player player;
    private int skin = 0;

    private TexturenIndex texturenIndex;

    private BitmapFont font;
    private String txt = "";

    BoldText clientIDLabel;

    public GameClass(final String name, String connectIP, String connetPort, TexturenIndex tx) {
        this.name = name;
        this.texturenIndex = tx;
        this.serverIP = connectIP;
        this.serverPort = connetPort;
    }

    @Override
    public void show () {
        batch = new SpriteBatch();

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

        // Server connection.

        player = new Player(0, 0, skin, name, texturenIndex);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(player.playerRec.x, player.playerRec.y, 0);
        cam.update();

        font = new BitmapFont();

        initClient();
        initUI();
        initClientUpdates();
    }

    private void initClient() {
        clientID = new Random().nextInt(99999);

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface I : Collections.list(interfaces)) {
                for (InetAddress addr : Collections.list(I.getInetAddresses())) {
                    if (addr instanceof Inet4Address) {
                        playerIP = addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Server wird betreten...  IP: " + serverIP + "; Port: " + serverPort + "; PlayerIP: " + playerIP);

        SocketHints hints = new SocketHints();
        client = Gdx.net.newClientSocket(Net.Protocol.TCP, serverIP, Integer.parseInt(serverPort), hints);
        try {
            client.getOutputStream().write(("Ping from Client[id:" + clientID + "]\n").getBytes());
            String joinString = "join:" + clientID + ";p:" + name + ";x:" + player.playerRec.x + ";y:" + player.playerRec.y + "\n";
            client.getOutputStream().write(joinString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (true) {
                    try {
                        String serverResponse = br.readLine();
                        if (serverResponse != null)
                            interpretor(serverResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void interpretor(String cmd) {
        System.err.println("Vom Server empfangen: " + cmd);
    }

    private void initClientUpdates() {
        Runnable clientSendRunnable = new Runnable() {
            @Override
            public void run() {
                while (isClientActive) {
                    String content = "cID:" + clientID + ";p:" + name + ";x:" + player.playerRec.x + ";y:" + player.playerRec.y + "\n";
                    try {
                        client.getOutputStream().write(content.getBytes());
                        Thread.sleep(300);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clientSend = new Thread(clientSendRunnable);
        clientSend.start();
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
                isClientActive = false;
                String leaveString = "cID:" + clientID + ";left\n";
                try {
                    client.getOutputStream().write(leaveString.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name, texturenIndex));
            }
        });

        stage.addActor(exitButton);

        clientIDLabel = new BoldText("Deine ClientID:" + clientID);

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
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
        player.update();
        player.updateGun(Gdx.input.getX(), Gdx.input.getY());

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

        player.gunSprite.draw(batch);
        player.playerSprite.draw(batch);

        font.draw(batch, "Deine ClientID: " + clientID, cam.position.x - cam.viewportWidth/2 + 8, cam.position.y + cam.viewportHeight/2 - 8);

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

        //  Kampf-Input
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

        }

        if (player.playerRec.getY() > cam.position.y + 40) {
            cam.translate(0, 4);
        } else if (player.playerRec.getX() < cam.position.x - (40 + MapData.MAP_TILE_SIZE)) {
            cam.translate(-4, 0);
        } else if (player.playerRec.getY() < cam.position.y - (40 + MapData.MAP_TILE_SIZE)) {
            cam.translate(0, -4);
        } else if (player.playerRec.getX() > cam.position.x + 40) {
            cam.translate(4, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
            }
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        stage.dispose();
        font.dispose();
    }
}