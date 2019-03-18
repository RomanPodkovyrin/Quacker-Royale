package com.anotherworld.model.ai;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;

import java.util.ArrayList;

public class AITools {
    //TODO make a walk to method

    public static void moveTo(PlayerData ai, Matrix destination) {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);
//        vector.normalizeThis();
//        ai.setXVelocity(vector.getX());
//        ai.setYVelocity(vector.getY());
        ai.setXVelocity((Maths.floatDivision(vector.getX(), Math.abs(vector.getX()))) );
        ai.setYVelocity((Maths.floatDivision(vector.getY(), Math.abs(vector.getY()))) );
        //        toCenter = new Matrix(Maths.floatDivision(toCenter.getX(), Math.abs(toCenter.getX())) * ai.getSpeed(),
//                Maths.floatDivision(toCenter.getY(), Math.abs(toCenter.getY())) * ai.getSpeed());

    }

}
