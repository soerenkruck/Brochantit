package de.brochantit.soerenkruck.Const;

import java.util.ArrayList;

public class MapData {
    public int MAP_X;
    public int MAP_Y;

    public int TIME;

    public static final int MAP_TILE_SIZE = 64;

    public static final int TIME_NIGHT = 0;
    public static final int TIME_EVENING = 1;
    public static final int TIME_DAY = 2;
    public static final int TIME_SIZE = 3;

    public static final String timeStrings[] = {"Mitternacht", "Abenddämmerung", "Tag"};

    public MapData(int MAP_X, int MAP_Y, int TIME) {
        this.MAP_X = MAP_X;
        this.MAP_Y = MAP_Y;
        this.TIME = TIME;
    }
}
