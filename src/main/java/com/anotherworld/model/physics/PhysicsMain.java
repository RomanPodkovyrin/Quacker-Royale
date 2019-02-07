//package com.anotherworld.model.physics;
//
//import com.anotherworld.model.ai.tools.Matrix;
//import com.anotherworld.model.ai.tools.MatrixMath;
//import com.anotherworld.model.movable.Ball;
//import com.anotherworld.model.movable.ObjectState;
//import com.anotherworld.model.movable.Player;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class PhysicsMain {
//
//    private static Logger logger = LogManager.getLogger(PhysicsMain.class);
//
//    public static void main(String[] args) {
//
//        logger.debug(Math.toDegrees(Math.atan2(0, -1)));
//        logger.debug(MatrixMath.vectorAngle(new Matrix(-3, -5)));
//        Physics p = new Physics(0.7f, 0.5f);
//        Ball ball = new Ball(4, 4, ObjectState.MOVING);
//        ball.setSpeed(1);
//        ball.setAngle(315);
//        ball.setXVelocity(-1);
//        ball.setYVelocity(0);
//        ball.setRadius(4);
//        ball.isDangerous(true);
//        Ball ball1 = new Ball (4, 4, ObjectState.MOVING);
//        ball.setSpeed(1);
//        ball.setAngle(315);
//        ball.setXVelocity(-1);
//        ball.setYVelocity(0);
//        ball.setRadius(4);
//        ball.setDangerous(true);
//        Ball ball2 = new Ball(4, 4, ObjectState.MOVING);
//        ball.setSpeed(1);
//        ball.setAngle(315);
//        ball.setXVelocity(-1);
//        ball.setYVelocity(0);
//        ball.setRadius(4);
//        ball.setDangerous(true);
//        // check for updating coordinate.
//        logger.debug(ball.isDangerous() + "" + ball.getXVelocity());
//        logger.debug(ball.getXCoordinate() + "" + ball.getYCoordinate());
//        Physics.move(ball);
//        logger.debug(ball.getXCoordinate() + "" + ball.getYCoordinate());
//        ball.setYVelocity(1.2f);
//        Physics.move(ball);
//        logger.debug(ball.getXCoordinate() + "" + ball.getYCoordinate());
//        // check for updating speed.
//        Physics.accelerate(ball);
//        logger.debug(ball.getSpeed());
//        Physics.applyFriction(ball);
//        logger.debug(ball.getSpeed());
//        Physics.applyFriction(ball);
//        logger.debug(ball.getSpeed());
//        Physics.applyFriction(ball);
//        // check for opposing outside force.
//        logger.debug(Physics.checkCollision(ball, ball1));
//        logger.debug(ball.getAngle() + " " + ball.getSpeed());
//        Physics.forceApplying(ball, new Matrix(1, 9));
//        logger.debug(ball.getAngle() + " " + ball.getSpeed());
//        // check if two balls are collided.
//        logger.debug(Physics.checkCollision(ball, ball1));
//        ball1.setCoordinates(19, 29);
//        logger.debug(Physics.checkCollision(ball, ball1));
//        // check if a ball is collided by the wall
//        // check if a player can collide by another player
//        // check if a player can be collided by a ball.
//    }
//}