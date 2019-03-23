package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.brochantit.soerenkruck.TexturenIndex;

public class SettingsScreen extends InteractingDefaultScreen {
    SettingsScreen(final TexturenIndex tx, final String defaultName) {
        super(tx, defaultName);

        TextButton btn = new TextButton("Hauptmenü", defaultDarkTextButtonStyle);
        btn.setBounds(Gdx.graphics.getWidth()-122, Gdx.graphics.getHeight()-36, 114, 28);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(defaultName, tx));
            }
        });
        defaultStage.addActor(btn);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        defaultBatch.begin();
        defaultFont.draw(defaultBatch, "Sich in Entwicklung befindend.", Gdx.graphics.getWidth()/2-150, Gdx.graphics.getHeight()/9*5);
        defaultBatch.end();
    }
}
