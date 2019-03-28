package com.anotherworld.view.data;

import com.anotherworld.view.graphics.spritesheet.HatSpriteSheet;
import com.anotherworld.view.graphics.spritesheet.PlayerSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.util.LinkedList;

/**
 * Stores and manages the data needed to display a player.
 * @author Jake Stewart
 *
 */
public class PlayerDisplayObject extends PlayerRectangleDisplayObject {
    
    private HealthBarDisplayObject healthBar;
    private PlayerRectangleDisplayObject hat;
    private PlayerRectangleDisplayObject arrow;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(Programme programme, PlayerDisplayData displayData, boolean currentPlayer) {
        super(new PlayerSpriteSheet(displayData), programme, displayData);
        this.healthBar = new HealthBarDisplayObject(programme, displayData);
        this.hat = new PlayerRectangleDisplayObject(new HatSpriteSheet(displayData), programme, displayData);
        this.arrow = new PlayerArrowDisplayObject(programme, displayData, currentPlayer);
    }
    
    @Override
    public LinkedList<DisplayObject> draw() {
        LinkedList<DisplayObject> result = super.draw();
        result.addAll(hat.draw());
        result.addAll(arrow.draw());
        result.addAll(healthBar.draw());
        return result;
    }
    
    @Override
    public boolean shouldDraw() {
        return true;
    }
    
    @Override
    public void destroy() {
        super.destroy();
        healthBar.destroy();
        hat.destroy();
        arrow.destroy();
    }
    
}
