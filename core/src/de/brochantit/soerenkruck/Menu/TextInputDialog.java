package de.brochantit.soerenkruck.Menu;

import com.badlogic.gdx.Input;

public class TextInputDialog implements Input.TextInputListener {

    private String value = "null";
    private String defaultValue = "null";

    @Override
    public void input(String text) {
        this.value = text;
    }

    public void setDeafultCanceledValue(final String value) {
        this.defaultValue = value;
    }
    @Override
    public void canceled() {
        this.value = defaultValue;
    }

    public String getValue() {
        return value;
    }
}
