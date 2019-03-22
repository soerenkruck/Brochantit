package de.brochantit.soerenkruck.Const;

import com.badlogic.gdx.graphics.Texture;
import de.brochantit.soerenkruck.Texturen;

public class PlayerSkin {

    public final static int PLAYER_STANDARD = 0;
    public final static int PLAYER_NINJEO = 1;
    public final static int PLAYER_GENJI = 2;

    public int playerSkin;

    public Texture left,
                    right,
                    up,
                    down;

    public PlayerSkin(int player, Texturen texturen) {
        this.playerSkin = player;

        if (player == PLAYER_STANDARD) {
            left = texturen.texture(Texturen.PLAYER_STANDARD_LEFT);
            right = texturen.texture(Texturen.PLAYER_STANDARD_RIGHT);
            up = texturen.texture(Texturen.PLAYER_STANDARD_UP);
            down = texturen.texture(Texturen.PLAYER_STANDARD_DOWN);
        } else if (player == PLAYER_NINJEO) {
            left = texturen.texture(Texturen.PLAYER_NINJEO_LEFT);
            right = texturen.texture(Texturen.PLAYER_NINJEO_RIGHT);
            up = texturen.texture(Texturen.PLAYER_NINJEO_UP);
            down = texturen.texture(Texturen.PLAYER_NINJEO_DOWN);
        } else if (player == PLAYER_GENJI) {
            left = texturen.texture(Texturen.PLAYER_GENJI_LEFT);
            right = texturen.texture(Texturen.PLAYER_GENJI_RIGHT);
            up = texturen.texture(Texturen.PLAYER_GENJI_UP);
            down = texturen.texture(Texturen.PLAYER_GENJI_DOWN);
        }
    }

}
