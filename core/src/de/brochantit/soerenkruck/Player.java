package de.brochantit.soerenkruck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import de.brochantit.soerenkruck.Const.MapData;
import de.brochantit.soerenkruck.Const.PlayerSkin;

public class Player {

    private int life = 100;
    public String name;
    PlayerSkin playerSkin;

    public Sprite playerSprite;
    public Rectangle playerRec, feetRectangle;
    public Sprite gunSprite;
    public float gunDeg;

    public String ip = "0";

    TexturenIndex tx;

    public Player(int x, int y, int skin, String name, TexturenIndex texturenIndex) {
        tx = texturenIndex;
        this.name = name;

        playerSkin = new PlayerSkin(skin, texturenIndex);

        playerSprite = new Sprite(playerSkin.right);
        if (playerSkin.playerSkin == PlayerSkin.PLAYER_STANDARD) {
            playerRec = new Rectangle(x, y, (MapData.MAP_TILE_SIZE/3*2)/2, MapData.MAP_TILE_SIZE/3*2);
        } else if (playerSkin.playerSkin == PlayerSkin.PLAYER_GENJI || playerSkin.playerSkin == PlayerSkin.PLAYER_NINJEO) {
            playerRec = new Rectangle(x, y, (MapData.MAP_TILE_SIZE/3*2), MapData.MAP_TILE_SIZE/3*2);
        }

        feetRectangle = new Rectangle(x, y, playerRec.width, 8);
        playerSprite.setBounds(playerRec.getX(), playerRec.getY(), playerRec.width, playerRec.height);

        gunSprite = new Sprite(texturenIndex.texture(TexturenIndex.GUN_STANDARD));
        gunSprite.setBounds(playerRec.x+playerRec.width/2, playerRec.y+playerRec.height/3, 16, 8);
        gunSprite.setOrigin(0, 4);
    }

    public void move(int x, int y, boolean turn) {
        if (turn) {
            if (playerRec.x < playerRec.x + x) {
                playerSprite.setTexture(playerSkin.right);
            } else if ((playerRec.x > playerRec.x + x)){
                playerSprite.setTexture(playerSkin.left);
            }
        }
        playerRec.setX(playerRec.x+x);
        playerRec.setY(playerRec.y+y);
        feetRectangle.setX(feetRectangle.x+x);
        feetRectangle.setY(feetRectangle.y+y);
    }

    public void set(int x, int y) {
        playerRec.setX(x);
        playerRec.setY(y);
        feetRectangle.setX(x);
        feetRectangle.setY(y);
        update();
    }

    public void update() {
        playerSprite.setPosition(playerRec.getX(), playerRec.getY());

    }

    public void updateGun(int mouseX, int mouseY) {
        float c = Math.abs(Gdx.graphics.getWidth()/2 - mouseX);
        if (c == 0)
            c = 0.1f;
        float a = Math.abs(Gdx.graphics.getHeight()/2 - mouseY);

        if (mouseX < Gdx.graphics.getWidth()/2) {
            gunDeg = 180 - (float) (Math.atan(a / c) / Math.PI) * 180;
            if (mouseY > Gdx.graphics.getHeight()/2) {
                gunDeg = 180 + (float) (Math.atan(a / c) / Math.PI) * 180;
            }
        } else {
            gunDeg = (float) (Math.atan(a / c) / Math.PI) * 180;
            if (mouseY > Gdx.graphics.getHeight()/2) {
                gunDeg = 360 - (float) (Math.atan(a / c) / Math.PI) * 180;
            }
        }

        gunSprite.setRotation(gunDeg);
        gunSprite.setPosition(playerRec.x+playerRec.width/2, playerRec.y+playerRec.height/3);
    }
}
