package com.anotherworld.view.data;

import com.anotherworld.view.graphics.spritesheet.HatSpriteSheet;
import com.anotherworld.view.graphics.spritesheet.PlayerSpriteSheet;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
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
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(Programme programme, PlayerDisplayData displayData) {
        this(new PlayerSpriteSheet(displayData), programme, displayData);
    }
    
    private PlayerDisplayObject(SpriteSheet spriteSheet, Programme programme, PlayerDisplayData displayData) {
        super(spriteSheet, programme, displayData);
        this.healthBar = new HealthBarDisplayObject(programme, displayData);
        this.hat = new PlayerRectangleDisplayObject(new HatSpriteSheet(displayData), programme, displayData);
    }
    
    @Override
    public LinkedList<DisplayObject> draw() {
        LinkedList<DisplayObject> result = super.draw();
        result.addAll(healthBar.draw());
        result.addAll(hat.draw());
        return result;
    }
    
}
