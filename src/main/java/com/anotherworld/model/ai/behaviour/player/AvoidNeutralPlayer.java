package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.maths.Maths;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class makes AI go around the player if one of them is abstraction the way.
 * @author roman
 */
public class AvoidNeutralPlayer extends Job {

    private static Logger logger = LogManager.getLogger(AvoidNeutralPlayer.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;

        for (Player player: players) {

            if (!player.isDead()) {

                // Checks the distance between the player and the ai
                if (MatrixMath.distanceAB(player.getCoordinates(), ai.getCoordinates()) <= player.getRadius() + ai.getRadius() + 0.5) {
                    fail();
                    logger.debug("Avoiding player " + player.getCharacterID());
                    Matrix vector = MatrixMath.pointsVector(player.getCoordinates(),ai.getCoordinates());
                    Line line = new Line(player.getCoordinates(), vector);
                    Matrix orthogonal = line.getOrthogonalVector();

                    ai.setXVelocity(Maths.floatDivision(orthogonal.getX(), Math.abs(orthogonal.getX())) * ai.getSpeed());
                    ai.setYVelocity(Maths.floatDivision(orthogonal.getY(), Math.abs(orthogonal.getY())) * ai.getSpeed());
                    return;
                }
            }
        }

        succeed();
        logger.trace("No player to avoid");

    }
}

