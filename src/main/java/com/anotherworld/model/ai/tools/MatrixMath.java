package com.anotherworld.model.ai.tools;

/**
 * This class contains mathematical manipulations between Line and Matrix
 * @author Roman P
 *
 */
public class MatrixMath {

    /**
     * Calculates inner product of the given Matrices
     * @param v1
     * @param v2
     * @return float the result of inner product between two matrices
     */
    public static float innerProduct(Matrix v1, Matrix v2) {

        return  (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }

    /**
     * Returns the magnitude of the given Matrix
     * @param v
     * @return float
     */
    public static float magnitude(Matrix v) {

        return (float) Math.sqrt(innerProduct(v,v));
    }

    /**
     * Returns the distance between the line and point
     * @param line
     * @param point
     * @return float
     */
    public static float dist(Line line, Matrix point) {
        float top =  -((line.getA() * point.getX()) + (line.getB() * point.getY()) - line.getD());
        float bottom = magnitude(line.getOrthogonalVector());

        float d = top / bottom;

        return d;
    }

    /**
     * Returns a boolean value indicating whether a perpendicular line can be draw between
     * the line and a point
     * @param vectorLine
     * @param vectorPoint
     * @param point
     * @return true if point has a nearest perpendicular neighbour, false if otherwise
     */
    public static boolean isPerpendicular(Matrix vectorLine, Matrix vectorPoint, Matrix point) {
        Matrix fromVectorPoint = new Matrix(point.getX() - vectorPoint.getX(), point.getY() - vectorPoint.getY());

        float vectorDirectionAngle = vectorAngle(vectorLine);
        float vectorToPointAngle = vectorAngle(fromVectorPoint);

        if (vectorToPointAngle <= vectorDirectionAngle - 90 || vectorToPointAngle >= vectorDirectionAngle + 90) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns and angle between the vector and North
     * @param vector
     * @return angle is returned in degrees
     */
    public static float vectorAngle(Matrix vector) {
        float angle = (float) Math.toDegrees(Math.atan2(vector.getX(), - vector.getY()));
        //System.out.println(angle);
        if (angle<0){
            angle += 360;
        }
        return angle;
    }

    /**
     * Returns the nearestNeighbour point
     * @param line
     * @param point
     * @return
     */
    public static Matrix nearestNeighbour(Line line, Matrix point) {

        Matrix normVector = line.getOrthogonalVector();

        return point.add((normVector.div(MatrixMath.magnitude(normVector))).mult(dist(line,point)));
    }

    /**
     * Calculates the vector between two points
     * @param startPoint
     * @param finishPoint
     * @return
     */
    public static Matrix pointsVector(Matrix startPoint, Matrix finishPoint) {
        return new Matrix(finishPoint.getX() - startPoint.getX(), finishPoint.getY() - startPoint.getY());

    }

    public static float distanceToNearestPoint(Line line, Matrix point) {
        return distanceAB(nearestNeighbour(line,point),point);

    }

    public static Matrix flipVector(Matrix vector) {
        return new Matrix(-vector.getX(),- vector.getY());
    }

    public static float distanceAB(Matrix a, Matrix b) {
        return Math.abs(magnitude(pointsVector(a,b)));
    }



}
