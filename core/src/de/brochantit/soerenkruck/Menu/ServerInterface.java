package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Server.Server;
import de.brochantit.soerenkruck.TexturenIndex;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class ServerInterface extends InteractingDefaultScreen {

    String ip, port = "1464";
    TextInputDialog tid;

    private boolean serverStable = false;

    ServerInterface(TexturenIndex tx, String name) {
        super(tx, name);

        tid = new TextInputDialog();
        tid.input(String.valueOf(port));

        TextButton btn = new TextButton("Hauptmenü", defaultDarkTextButtonStyle);
        btn.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-36, 114, 28);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(defaultName, defaultTexturenIndex));
            }
        });

        TextButton serverStartButton = new TextButton("Server starten.", defaultLightTextButtonStyle);
        serverStartButton.setBounds(Gdx.graphics.getWidth()/2-64, Gdx.graphics.getHeight()/2, 128, 48);
        serverStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //serverStart();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Server server = new Server(port);
                        server.start();

                        while (!server.isRunningStable){
                        }

                        serverStable = true;

                    }
                }).start();

            }
        });

        TextButton portChangeButton = new TextButton("Port wählen", defaultLightTextButtonStyle);
        portChangeButton.setBounds(294, Gdx.graphics.getHeight()-84, 96, 28);
        portChangeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(tid, "Port wählen", port, "");
            }
        });

        defaultStage.addActor(btn);
        defaultStage.addActor(serverStartButton);
        defaultStage.addActor(portChangeButton);

        ip = null;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface I : Collections.list(interfaces)) {
                for (InetAddress addr : Collections.list(I.getInetAddresses())) {
                    if (addr instanceof Inet4Address) {
                        ip = addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (port.length() > 0) {
            port = tid.getValue();
        }

        defaultBatch.begin();
        defaultFont.draw(defaultBatch, "Server IP: " + ip + " | Port: " + port, 32, Gdx.graphics.getHeight()-28);

        if (serverStable)
            defaultBoldFont.draw(defaultBatch, "Server erfolgreich gestartet auf " + ip + ", auf dem Port " + port, 16, 32);

        defaultBatch.end();
    }

    private void serverStart() {
        Server server = new Server(port);
        server.start();
        //server.startSession();
        //server.test();
    }
}
