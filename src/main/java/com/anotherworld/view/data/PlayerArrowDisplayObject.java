package com.anotherworld.view.data;

import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

public class PlayerArrowDisplayObject extends PlayerRectangleDisplayObject {
    
    private static final int DISPLAY_TIME = 2;

    private PlayerDisplayData displayData;
    
    private long createTime;
    
    private boolean currentPlayer;
    
    /**
     * Creates an arrow object to indicate which player you control.
     * @param programme The programme to use for rendering
     * @param displayData The player data
     * @param currentPlayer If the arrow is for the current player
     */
    public PlayerArrowDisplayObject(Programme programme, PlayerDisplayData displayData, boolean currentPlayer) {
        super(new ArrowSpriteSheet(), programme, displayData);
        this.displayData = displayData;
        createTime = SpriteSheet.getCurrentTime();
        this.currentPlayer = currentPlayer;
    }
    
    @Override
    public float getY() {
        return displayData.getYCoordinate() - displayData.getRadius() * 2;
    }
    
    @Override
    public boolean shouldDraw() {
        return currentPlayer && (SpriteSheet.getCurrentTime() - createTime) / 1000 < DISPLAY_TIME;
    }

}
