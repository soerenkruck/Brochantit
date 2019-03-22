package de.brochantit.soerenkruck.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import de.brochantit.soerenkruck.Player;
import de.brochantit.soerenkruck.Texturen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Server {

    String txt = "";
    public ArrayList<Player> sessionPlayer;
    Texturen texturen;

    public int port;

    ServerSocketHints ssh;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader buffer;

    public Server(int port, Texturen texturen) {
        this.texturen = texturen;
        this.port = port;
        final int tmpPort = this.port;

        sessionPlayer = new ArrayList<Player>();

        ssh = new ServerSocketHints();
        ssh.acceptTimeout = 0;
        serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, tmpPort, ssh);
        socket = serverSocket.accept(null);
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        txt = buffer.readLine();
                        System.out.println(txt);
                        interpretor(txt);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void interpretor(String txt) {
        if (txt.startsWith("connect=")) {
            String t = txt.replace("connect=", "");
            String tmp[] = t.split(";");
            sessionPlayer.add(new Player(0, 0, 0, tmp[0], texturen)); //TODO: PlayerSkins
            sessionPlayer.get(sessionPlayer.size()-1).ip = tmp[1];
            System.err.println("new player");
        }
        if (txt.startsWith("COORD=")) {
            String tmpS = txt.replace("COORD=", "").replace("\n", "");
            String lines[] = tmpS.split(";");
            for (int i = 0; i <= sessionPlayer.size()-1; i++) {
                if (lines[2].equals(sessionPlayer.get(i).name)) {
                    sessionPlayer.get(i).set(Math.round(Float.valueOf(lines[0])), Math.round(Float.valueOf(lines[1])));
                    break;
                }
            }
        }
    }
}
