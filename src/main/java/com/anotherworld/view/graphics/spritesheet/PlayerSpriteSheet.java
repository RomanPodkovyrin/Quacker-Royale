package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.view.data.PlayerDisplayData;

public class PlayerSpriteSheet extends AbstractPlayerSpriteSheet {
    
    private PlayerDisplayData data;
    
    /**
     * Creates a spritesheet using the player data to manage the current player sprite.
     * @param data the player data to use
     */
    public PlayerSpriteSheet(PlayerDisplayData data) {
        super(data);
        this.data = data;
        
    }

    @Override
    public int getEffectOffsetAmount() {
        if (data.isShielded()) {
            return 1;
        }
        return 0;
    }
    
}
