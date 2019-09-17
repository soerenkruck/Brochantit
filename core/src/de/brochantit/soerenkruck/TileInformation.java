package de.brochantit.soerenkruck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import de.brochantit.soerenkruck.Const.GroundTypes;
import de.brochantit.soerenkruck.Const.MapData;

public class TileInformation {
    public int type;
    public boolean isStatic;

    public Sprite sprite;
    public Rectangle rec;

    TexturenIndex texturenIndex;

    public TileInformation (int t, boolean isStatic, TexturenIndex tx, int x, int y) {
        this.type = t;
        this.isStatic = isStatic;
        this.texturenIndex = tx;

        if (type != 0) {
            if (type == GroundTypes.GRASS) {
                sprite = new Sprite(texturenIndex.texture(TexturenIndex.GRASS));
            } else if (type == GroundTypes.WALL) {
                sprite = new Sprite(texturenIndex.texture(TexturenIndex.WALL));
            } else if (type == GroundTypes.FENCE) {
                sprite = new Sprite(texturenIndex.texture(TexturenIndex.FENCE));
            }
        } else {
            sprite = new Sprite(new Texture(Gdx.files.internal("textures/null.png")));
        }
        rec = new Rectangle(x*MapData.MAP_TILE_SIZE, y*MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE, MapData.MAP_TILE_SIZE);
        sprite.setBounds(rec.x, rec.y, rec.width, rec.height);
    }

    public void update() {
        if (type != 0) {
            if (type == GroundTypes.GRASS) {
                sprite.setTexture(texturenIndex.texture(TexturenIndex.GRASS));
            } else if (type == GroundTypes.WALL) {
                sprite.setTexture(texturenIndex.texture(TexturenIndex.WALL));
            } else if (type == GroundTypes.FENCE) {
                sprite.setTexture(texturenIndex.texture(TexturenIndex.FENCE));
            }
        } else {
            sprite.setTexture(new Texture(Gdx.files.internal("textures/null.png")));
        }
    }
}
