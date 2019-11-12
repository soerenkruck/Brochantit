package de.brochantit.soerenkruck.Server;

import com.badlogic.gdx.math.Rectangle;

public class ServerPlayer {

    public Rectangle rec;
    public int clientID;

    public ServerPlayer(String cID, int x, int y) {
        rec = new Rectangle(x, y, 1, 1);
        this.clientID = Integer.valueOf(cID);
    }
}
