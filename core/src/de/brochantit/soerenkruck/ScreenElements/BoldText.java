package de.brochantit.soerenkruck.ScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BoldText extends Actor {

    BitmapFont font;
    String text;

    public BoldText(String text) {
        font = new BitmapFont(Gdx.files.internal("fonts/share-bold.fnt"));
        font.setColor(0.8f, 0.8f, 0.7f, 1);
        this.text = text;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        font.draw(batch, text, 0, 0);
    }

    public Actor hit(float x, float y) {
        return null;
    }
}
