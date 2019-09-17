package de.brochantit.soerenkruck;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class TexturenIndex {

    public static final int GROUND_STANDARD = 0;
    public static final int WALL = 1;
    public static final int GRASS = 2;
    public static final int FENCE = 18;

    public static final int EDITOR_SELECT = 5;

    public static final int PLAYER_STANDARD_LEFT = 3;
    public static final int PLAYER_STANDARD_RIGHT = 4;
    public static final int PLAYER_STANDARD_UP = 6;
    public static final int PLAYER_STANDARD_DOWN = 7;
    public static final int PLAYER_NINJEO_LEFT = 8;
    public static final int PLAYER_NINJEO_RIGHT = 9;
    public static final int PLAYER_NINJEO_UP = 10;
    public static final int PLAYER_NINJEO_DOWN = 11;
    public static final int PLAYER_GENJI_LEFT = 12;
    public static final int PLAYER_GENJI_RIGHT = 13;
    public static final int PLAYER_GENJI_UP = 14;
    public static final int PLAYER_GENJI_DOWN = 15;

    public static final int GUN_STANDARD = 16;

    public static final int BULLET_STANDARD = 17;

    private ArrayList<Texture> textures;

    public TexturenIndex() {
        textures = new ArrayList<Texture>();
    }

    public void load() {
        textures.add(new Texture("textures/ground/gras.png"));
        textures.add(new Texture("textures/placeable/wall.png"));
        textures.add(new Texture("textures/placeable/gras.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player3_invert.png"));
        textures.add(new Texture("textures/ui/select/editor_fieldSelect.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player.png"));
        textures.add(new Texture("textures/player/player_invert.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player2.png"));
        textures.add(new Texture("textures/player/player2_invert.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/player/player3.png"));
        textures.add(new Texture("textures/GameObjects/gun.png"));
        textures.add(new Texture("textures/GameObjects/bullet.png"));
        textures.add(new Texture("textures/placeable/fence.png"));
    }

    public Texture texture (int e) {
        return textures.get(e);
    }
}
