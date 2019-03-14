package com.anotherworld.view;

import com.anotherworld.view.data.PlayerDisplayData;

public class PlayerSpriteSheet extends SpriteSheet {

    private final PlayerDisplayData data;
    
    private static final int BASE_ID = 0;
    
    private static final int MAX_ID = 4;
    
    private static final int DIRECTION_OFFSET = 0;
    
    private static final long FRAME_TIME = 200;
    
    public PlayerSpriteSheet(PlayerDisplayData data) {
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }

    @Override
    public int getTextureId() {
        int id = (int)((SpriteSheet.getCurrentTime() / FRAME_TIME) % Math.max(MAX_ID + 1, 1)) + BASE_ID;
        return id;
    }

    @Override
    public int getTextureBuffer() {
        return 1;
    }
    
}
