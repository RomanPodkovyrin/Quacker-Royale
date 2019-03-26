package com.anotherworld.view.graphics.layout;

import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.input.ButtonData;

public class LobbyLayout extends FixedSpaceLayout {

    public LobbyLayout(float buttonHeight) {
        super(buttonHeight);
    }
    
    /**
     * Add a TextList to the layout.
     * @param b The text list to add
     */
    public void addList(TextListData b) {
        for (ButtonData button : b.getButtons()) {
            this.addButton(button);
        }
    }

}
