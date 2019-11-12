package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.Game.GameClass;
import de.brochantit.soerenkruck.TexturenIndex;

public class JoinScreen extends InteractingDefaultScreen {

    String name;

    final float ABS_16 = Gdx.graphics.getHeight()/67.5f;

    String ip = "0", port = "0";
    TextInputDialog ipListener, portListener;

    JoinScreen(TexturenIndex tx, final String name) {
        super(tx, name);

        final TexturenIndex texturenIndex = tx;

        TextButton btn = new TextButton("Hauptmenü", defaultDarkTextButtonStyle);
        btn.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-36, 114, 28);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(defaultName, defaultTexturenIndex));
            }
        });

        TextButton startButton = new TextButton("Server betreten.", defaultLightTextButtonStyle);
        startButton.setBounds(Gdx.graphics.getWidth()/2-64, Gdx.graphics.getHeight()/2, 128, 48);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameClass(name, ip, port, texturenIndex));
            }
        });

        portListener = new TextInputDialog();
        Gdx.input.getTextInput(portListener, "Server Port", "1234", "");
        ipListener = new TextInputDialog();
        Gdx.input.getTextInput(ipListener, "Zieladresse", "127.0.0.1", "");

        defaultStage.addActor(btn);
        defaultStage.addActor(startButton);

        // TODO: Verfügbarkeit eines Servers überprüfen.
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ip = ipListener.getValue();
        port = portListener.getValue();

        defaultBatch.begin();
        //defaultFont.draw(defaultBatch, "Einem Server beitreten.", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 9 * 5);
        if (ip.length() > 1 && port.length() > 1) {
            defaultBoldFont.draw(defaultBatch, "IP: " + ip + " | Port: " + port, 32, Gdx.graphics.getHeight()-32);
        }
        defaultBatch.end();
    }


}
