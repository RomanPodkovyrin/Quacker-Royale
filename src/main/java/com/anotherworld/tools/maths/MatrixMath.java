package com.anotherworld.tools.maths;

/**
 * This class contains mathematical manipulations between Line and Matrix.
 *
 * @author Roman P
 *
 */
public class MatrixMath {

    /**
     * Calculates inner product of the given Matrices.
     *
     * @param v1 One of the matrices
     * @param v2 One of the matrices
     * @return float the result of inner product between two matrices
     */
    public static float innerProduct(Matrix v1, Matrix v2) {

        return  (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }

    /**
     * Returns the magnitude of the given Matrix.
     *
     * @param v The vector in form of a matrix
     * @return float the magnitude of the matrix as a float
     */
    public static float magnitude(Matrix v) {

        return (float) Math.sqrt(innerProduct(v,v));
    }

    /**
     * Returns the distance between the line and point.
     *
     * @param line measure form the line
     * @param point to the point
     * @return float the shortest distance between the line and a point
     */
    public static float dist(Line line, Matrix point) {
        float top =  -((line.getA() * point.getX()) + (line.getB() * point.getY()) - line.getD());
        float bottom = magnitude(line.getOrthogonalVector());

        return Maths.floatDivision(top, bottom);
    }

    /**
     * Returns a boolean value indicating whether a perpendicular line can be draw between
     * the line and a point.
     *
     * @param vectorLine the line to be checked
     * @param vectorPoint the starting point of the line
     * @param point point to be checked against the line
     * @return true if point has a nearest perpendicular neighbour, false if otherwise
     */
    public static boolean isPerpendicular(Matrix vectorLine, Matrix vectorPoint, Matrix point) {
        Matrix fromVectorPoint = new Matrix(point.getX() - vectorPoint.getX(), point.getY() - vectorPoint.getY());

        float vectorDirectionAngle = vectorAngle(vectorLine);
        float vectorToPointAngle = vectorAngle(fromVectorPoint);

        return !(vectorToPointAngle <= (vectorDirectionAngle - 90)) && !(vectorToPointAngle >= vectorDirectionAngle + 90);
    }

    /**
     * Returns and angle between the vector and North.
     *
     * @param vector The vector to get the angel between it and North
     * @return angle is returned in degrees
     */
    public static float vectorAngle(Matrix vector) {
        float angle = (float) Math.toDegrees(Math.atan2(vector.getX(), - vector.getY()));
        //System.out.println(angle);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Returns the nearestNeighbour point.
     *
     * @param line The line next to the point
     * @param point the point near the line
     * @return Returns the Matrix of the point on the line
     */
    public static Matrix nearestNeighbour(Line line, Matrix point) {

        Matrix normVector = line.getOrthogonalVector();

        return point.add((normVector.div(MatrixMath.magnitude(normVector))).mult(dist(line,point)));
    }

    /**
     * Calculates the vector between two points.
     *
     * @param startPoint The starting point
     * @param finishPoint the finish point
     * @return The vector from start to finish
     */
    public static Matrix pointsVector(Matrix startPoint, Matrix finishPoint) {
        return new Matrix(finishPoint.getX() - startPoint.getX(), finishPoint.getY() - startPoint.getY());

    }

    /**
     * Returns the distance to the nearest point between the line and the point.
     *
     * @param line The line next to the point
     * @param point The point near the line
     * @return The distance between the line and the point
     */
    public static float distanceToNearestPoint(Line line, Matrix point) {
        return distanceAB(nearestNeighbour(line,point),point);

    }

    /**
     * Flips the Matrix by inverting x and y.
     *
     * @param matrix The Matrix to be flipped
     * @return Returns the new Matrix with the inverted x and y
     */
    public static Matrix flipMatrix(Matrix matrix) {
        return new Matrix(-matrix.getX(),- matrix.getY());
    }

    /**
     * Gets the distance between point A and B.
     *
     * @param a The starting point
     * @param b The finishing point
     * @return The distance between the points
     */
    public static float distanceAB(Matrix a, Matrix b) {
        return Math.abs(magnitude(pointsVector(a,b)));
    }



}
