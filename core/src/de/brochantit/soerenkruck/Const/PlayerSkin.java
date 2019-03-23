package de.brochantit.soerenkruck.Const;

import com.badlogic.gdx.graphics.Texture;
import de.brochantit.soerenkruck.TexturenIndex;

public class PlayerSkin {

    public final static int PLAYER_STANDARD = 0;
    public final static int PLAYER_NINJEO = 1;
    public final static int PLAYER_GENJI = 2;

    public int playerSkin;

    public Texture left,
                    right,
                    up,
                    down;

    public PlayerSkin(int player, TexturenIndex texturenIndex) {
        this.playerSkin = player;

        if (player == PLAYER_STANDARD) {
            left = texturenIndex.texture(TexturenIndex.PLAYER_STANDARD_LEFT);
            right = texturenIndex.texture(TexturenIndex.PLAYER_STANDARD_RIGHT);
            up = texturenIndex.texture(TexturenIndex.PLAYER_STANDARD_UP);
            down = texturenIndex.texture(TexturenIndex.PLAYER_STANDARD_DOWN);
        } else if (player == PLAYER_NINJEO) {
            left = texturenIndex.texture(TexturenIndex.PLAYER_NINJEO_LEFT);
            right = texturenIndex.texture(TexturenIndex.PLAYER_NINJEO_RIGHT);
            up = texturenIndex.texture(TexturenIndex.PLAYER_NINJEO_UP);
            down = texturenIndex.texture(TexturenIndex.PLAYER_NINJEO_DOWN);
        } else if (player == PLAYER_GENJI) {
            left = texturenIndex.texture(TexturenIndex.PLAYER_GENJI_LEFT);
            right = texturenIndex.texture(TexturenIndex.PLAYER_GENJI_RIGHT);
            up = texturenIndex.texture(TexturenIndex.PLAYER_GENJI_UP);
            down = texturenIndex.texture(TexturenIndex.PLAYER_GENJI_DOWN);
        }
    }

}
