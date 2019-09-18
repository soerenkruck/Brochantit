package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.TexturenIndex;

public class JoinScreen extends InteractingDefaultScreen {

    String name;

    final float ABS_16 = Gdx.graphics.getHeight()/67.5f;

    JoinScreen(TexturenIndex tx, final String name) {
        super(tx, name);

        TextButton btn = new TextButton("Hauptmenü", defaultDarkTextButtonStyle);
        btn.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-36, 114, 28);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(defaultName, defaultTexturenIndex));
            }
        });
        defaultStage.addActor(btn);

        // TODO: Verfügbarkeit eines Servers überprüfen.
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        defaultBatch.begin();
        defaultFont.draw(defaultBatch, "Einem Server beitreten.", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 9 * 5);
        defaultBatch.end();
    }
}
