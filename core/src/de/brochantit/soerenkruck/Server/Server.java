package de.brochantit.soerenkruck.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import de.brochantit.soerenkruck.Player;
import de.brochantit.soerenkruck.TexturenIndex;

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

    String txt = "";
    public ArrayList<Player> sessionPlayer;
    TexturenIndex texturenIndex;

    public int port;

    public Server(int port) {
        this.port = port;
    }

    public void start(final int port) {
        this.port = port;

        new Thread(new Runnable() {
            int tmpPort = port;
            @Override
            public void run() {
                ServerSocketHints serverSocketHints = new ServerSocketHints();
                serverSocketHints.acceptTimeout = 0;

                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, tmpPort, serverSocketHints);

                System.out.println("Server gestartet...; auf Port: " + tmpPort);

                while (true) {
                    Socket socket = serverSocket.accept(null);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        String t = bufferedReader.readLine();
                        System.out.println(t);
                        if (t.startsWith("ServerTestNachricht")) {
                            System.err.println("Server wurde erfolgreich gestartet.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void test() {
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 1000;

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

        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, socketHints);
        String testString = "ServerTestNachricht; an IP=" + ip + " auf Port " + port + "\n";

        try {
            socket.getOutputStream().write(testString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
