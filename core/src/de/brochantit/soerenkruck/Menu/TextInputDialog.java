package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Input;

public class TextInputDialog implements Input.TextInputListener {

    private String value = "0";

    @Override
    public void input(String text) {
        this.value = text;
    }

    @Override
    public void canceled() {
        this.value = "127.0.0.1";
    }

    public String getValue() {
        return value;
    }
}
