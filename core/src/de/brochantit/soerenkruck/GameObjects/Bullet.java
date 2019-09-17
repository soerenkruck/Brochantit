package de.brochantit.soerenkruck.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.brochantit.soerenkruck.TexturenIndex;

public class Bullet {

    private int x, y;
    private Vector2 dir;
    private int speed;
    private TexturenIndex tx;

    public Sprite sprite;

    Bullet (int x, int y, Vector2 dir, float speed, TexturenIndex tx) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tx = tx;

        sprite = new Sprite(tx.texture(TexturenIndex.BULLET_STANDARD));
        sprite.setBounds(this.x, this.y, 16, 8);
    }

    public void update() {
        x = (int) dir.x * speed;
        y = (int) dir.y * speed;

        sprite.setPosition(x, y);
    }

}
