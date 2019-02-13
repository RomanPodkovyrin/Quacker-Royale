package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ChasePlayer extends Job {

    private static Logger logger = LogManager.getLogger(ChasePlayer.class);

    public  ChasePlayer() {
        super();
    }
    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        // Theoretically if this job comes after avoiding balls
        // the balls array should already be sorted by the distance from the player
        // NOOOOOOOOOOO
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        players = sortObject(players);

        logger.debug("Starting ChasePlayer Job");
        for (Player player: players){
            if (isRunning()) {
                logger.debug("Chasing the Player");
                Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), player.getCoordinates());
                if (MatrixMath.distanceAB(ai.getCoordinates(),player.getCoordinates()) <= player.getRadius() + ai.getRadius() + 15) {
                    succeed();
                    return;
                }
                if (vector.getX() != 0) {
                    ai.setXVelocity((vector.getX() / Math.abs(vector.getX())) * ai.getSpeed());
                }
                if (vector.getY() != 0) {
                    ai.setYVelocity((vector.getY() / Math.abs(vector.getY())) * ai.getSpeed());
                }
                fail();
                return;
            } else {
                logger.debug("Finishing ChasePlayer with fail: nothing to chase");
                ai.setXVelocity(0);
                ai.setYVelocity(0);
                fail();
                return;
            }
        }

    }

    /**
     * Sorts balls based on their distance from the AI player.
     *
     * @param objects The object to be sorted based on the distance from the AI
     * @return returns an ArrayList of Balls starting with the closes one
     */
    private ArrayList<Player> sortObject(ArrayList<Player> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
        return objects;
    }
}
