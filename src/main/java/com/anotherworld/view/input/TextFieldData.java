package com.anotherworld.view.input;

import com.anotherworld.tools.input.KeyListener;

import java.util.Optional;

public class TextFieldData extends ButtonData {

    private Optional<StringKeyListener> kl = Optional.empty();
    
    public TextFieldData(String text, StringKeyListener object) {
        super(text);
        this.kl = Optional.of(object);
    }
    
    @Override
    public String getText() {
        if (!kl.isPresent()) {
            return super.getText();
        }
        return kl.get().getCurrentString();
    }

}
