package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.view.data.PlayerDisplayData;

public class HatSpriteSheet extends AbstractPlayerSpriteSheet {

    private final PlayerDisplayData data;
    
    public HatSpriteSheet(PlayerDisplayData data) {
        super(data);
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }

    @Override
    public SpriteLocation getTextureBuffer() {
        switch (data.getObjectID().toLowerCase()) { //TODO switch this to real method
            case "santa":
                return SpriteLocation.SANTA_HAT;
            case "pirate":
                return SpriteLocation.PIRATE_HAT;
            case "robber":
                return SpriteLocation.ROBBER_HAT;
            case "police":
                return SpriteLocation.POLICE_HAT;
            default:
                return SpriteLocation.SANTA_HAT;
        }
    }

    @Override
    public int getEffectOffsetAmount() {
        return 0;
    }
    
}
