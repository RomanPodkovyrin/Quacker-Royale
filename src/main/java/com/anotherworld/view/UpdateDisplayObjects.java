package com.anotherworld.view;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.data.BallDisplayData;
import com.anotherworld.view.data.PlayerDisplayData;
import com.anotherworld.view.data.RectangleDisplayData;

import java.util.ArrayList;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
class UpdateDisplayObjects implements ViewEvent {
    
    private final ArrayList<? extends PlayerDisplayData> playerObjects;
    private final ArrayList<? extends BallDisplayData> ballObjects;
    private final ArrayList<? extends RectangleDisplayData> rectangleObjects;
    private final ArrayList<? extends WallData> wallObjects;
    
    public UpdateDisplayObjects(ArrayList<? extends PlayerDisplayData> playerObjects,
            ArrayList<? extends BallDisplayData> ballObjects,
            ArrayList<? extends RectangleDisplayData> rectangleObjects, ArrayList<? extends WallData> wallObjects) {
        this.playerObjects = playerObjects;
        this.ballObjects = ballObjects;
        this.rectangleObjects = rectangleObjects;
        this.wallObjects = wallObjects;
    }

    /**
     * Returns the new player objects for the view.
     * @return the new objects
     */
    public ArrayList<? extends PlayerDisplayData> getPlayerObjects() {
        return playerObjects;
    }

    /**
     * Returns the new ball objects for the view.
     * @return the new objects
     */
    public ArrayList<? extends BallDisplayData> getBallObjects() {
        return ballObjects;
    }

    /**
     * Returns the new rectangle objects for the view.
     * @return the new objects
     */
    public ArrayList<? extends RectangleDisplayData> getRectangleObjects() {
        return rectangleObjects;
    }

    /**
     * Returns the new wall objects for the view.
     * @return the new objects
     */
    public ArrayList<? extends WallData> getWallObjects() {
        return wallObjects;
    }

}
