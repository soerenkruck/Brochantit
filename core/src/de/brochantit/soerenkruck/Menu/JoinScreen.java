package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.TexturenIndex;

public class JoinScreen extends InteractingDefaultScreen {

    String name;

    final float ABS_16 = Gdx.graphics.getHeight()/67.5f;

    String ip = "0";
    TextInputDialog listener;

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

        listener = new TextInputDialog();
        Gdx.input.getTextInput(listener, "Zieladresse", "127.0.0.1", "");

        defaultStage.addActor(btn);

        // TODO: Verfügbarkeit eines Servers überprüfen.
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ip = listener.getValue();

        defaultBatch.begin();
        defaultFont.draw(defaultBatch, "Einem Server beitreten.", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 9 * 5);
        if (ip.length() > 1) {
            defaultBoldFont.draw(defaultBatch, "IP: " + ip, 32, Gdx.graphics.getHeight()-32);
        }
        defaultBatch.end();
    }


}
