package com.anotherworld.model.ai;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.PlayerData;

import java.util.ArrayList;

public class AITools {
    //TODO make a walk to method

    public static void moveTo(PlayerData ai, Matrix destination) {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);
        vector.normalizeThis();
        ai.setXVelocity(vector.getX() * ai.getSpeed());
        ai.setYVelocity(vector.getY() * ai.getSpeed());
    }

}
