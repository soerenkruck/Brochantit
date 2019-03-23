package de.brochantit.soerenkruck;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import de.brochantit.soerenkruck.Menu.MainMenu;

public class Splash implements Screen {

    SpriteBatch batch;
    BitmapFont font;

    final int DURATION = 4000;
    boolean finished = false;

    Sprite texture;
    Rectangle iconRec;
    Color color;

    String name;
    TexturenIndex texturenIndex;

    Splash (String name) {
        this.name = name;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        texture = new Sprite(new Texture(Gdx.files.local("textures/player/player3_invert.png")));
        iconRec = new Rectangle(Gdx.graphics.getWidth()/2-Gdx.graphics.getHeight()/2/2/2/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/2/2/2, Gdx.graphics.getHeight()/2/2);
        texture.setBounds(iconRec.x, iconRec.y, iconRec.width, iconRec.height);
        color = texture.getColor();

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                texturenIndex = new TexturenIndex();
                texturenIndex.load();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finished = true;
            }
        }).start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.97f, 0.98f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        iconRec.width *= 1.001;
        iconRec.height = iconRec.width*2;
        iconRec.x = Gdx.graphics.getWidth()/2-(iconRec.width/2);
        iconRec.y = Gdx.graphics.getHeight()/2-(iconRec.height/2);
        texture.setBounds(iconRec.x, iconRec.y, iconRec.width, iconRec.height);

        texture.setColor(color.r, color.g, color.b, color.a);
        batch.enableBlending();
        batch.begin();
        texture.draw(batch);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            finished = true;

        if (finished)
            color.a -= 0.018;

        if (color.a < 0.01)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(name, texturenIndex));

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}


