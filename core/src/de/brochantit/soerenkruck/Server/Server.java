package de.brochantit.soerenkruck.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Server {

    Socket socket;

    public int port;
    public String serverIP;

    public boolean isRunningStable = false;

    private ArrayList<ServerPlayer> playerArrayList;

    public Server(String port) {
        this.port = Integer.valueOf(port);

        playerArrayList = new ArrayList<ServerPlayer>();
    }

    public void start() {
        new Thread(new Runnable() {
            int tmpPort = Integer.valueOf(port);
            @Override
            public void run() {
                ServerSocketHints serverSocketHints = new ServerSocketHints();
                serverSocketHints.acceptTimeout = 0;

                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, port, serverSocketHints);

                System.out.println("Server wird gestartet...; auf Port: " + tmpPort);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();

                while (true) {
                    socket = serverSocket.accept(null);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            while (true) {
                                try {
                                    String cmdString = br.readLine();
                                    if (cmdString != null) {
                                        //System.out.println(tmpSt);
                                        interpretor(cmdString);
                                    }

                                    for (int i = 0; i < playerArrayList.size(); i++) {
                                        String sendString = "cID:" + playerArrayList.get(i).clientID + ";x:" + playerArrayList.get(i).rec.x + ";y:" + playerArrayList.get(i).rec.y + "\n";
                                        socket.getOutputStream().write(sendString.getBytes());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        }).start();

        test();
    }

    public void test() {
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 5000;

        String ip = null;

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
        
        System.out.println(ip);

        serverIP = ip;

        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, serverIP, port, socketHints);
        String testString = "ServerTestNachricht;\n";

        try {
            socket.getOutputStream().write(testString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void interpretor(String cmd) {
        System.out.println("received msg: " + cmd);

        String cmdContent[] = cmd.split(";");

        if (cmdContent[0].startsWith("ServerTestNachricht"))
            isRunningStable = true;

        if (cmdContent[0].startsWith("join")) {
            String cID = cmdContent[0].replace("join:", "");
            int x = Math.round(Float.valueOf(cmdContent[2].replace("x:", "")));
            int y = Math.round(Float.valueOf(cmdContent[3].replace("y:", "")));
            playerArrayList.add(new ServerPlayer(cID, x, y));
            System.err.println("Neuer Spieler hinzugefügt.");
        }

        if (cmdContent[0].startsWith("cID:")) {
            String cID = cmdContent[0].replace("cID:", "");
            int x = Math.round(Float.valueOf(cmdContent[2].replace("x:", "")));
            int y = Math.round(Float.valueOf(cmdContent[3].replace("y:", "")));
            playerArrayList.add(new ServerPlayer(cID, x, y));
            //System.err.println("Spieler geupdated.");
        }

        if (cmdContent.length > 1) {
            if (cmdContent[1].startsWith("left")) {
                int tmpCID = Integer.valueOf(cmdContent[0].replace("cID:", ""));
                for (int i = 0; i < playerArrayList.size(); i++) {
                    if (playerArrayList.get(i).clientID == tmpCID) {
                        playerArrayList.remove(i);
                        System.err.println("Spieler " + tmpCID + " wurde entfernt.");
                    }
                }
            }
        }
    }
}