package com.anotherworld.model.ai.aiMathsTools;

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
    public static float innerProduct(Matrix v1 , Matrix v2){

        return  (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }

    /**
     * Returns the magnitude of the given Matrix
     * @param v
     * @return float
     */
    public static float magnitude(Matrix v){

        return (float) Math.sqrt(innerProduct(v,v));
    }

    /**
     * Returns the distance between the line and point
     * @param line
     * @param point
     * @return float
     */
    public static float dist(Line line, Matrix point){

        float d = -((line.getA() * point.getX()) + (line.getB() * point.getY()) - line.getD())/magnitude(line.getOrthogonalVector());

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
    public static boolean isPerpendicular(Matrix vectorLine, Matrix vectorPoint, Matrix point){
        Matrix fromVectorPoint = new Matrix(point.getX() - vectorPoint.getX(), point.getY() - vectorPoint.getY());

        float vectorDirectionAngle = vectorAngle(vectorLine);
        float vectorToPointAngle = vectorAngle(fromVectorPoint);

        if (vectorToPointAngle <= vectorDirectionAngle - 90 || vectorToPointAngle >= vectorDirectionAngle + 90){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Returns and angle between the vector and North
     * @param vector
     * @return angle is returned in degrees
     */
    public static float vectorAngle(Matrix vector){

        return 90 + (float) Math.toDegrees(Math.atan2(vector.getY() , vector.getX()));
    }

    /**
     * Returns the nearestNeighbour point
     * @param line
     * @param point
     * @return
     */
    public static Matrix nearestNeighbour(Line line, Matrix point){

        Matrix normVector = line.getOrthogonalVector();

        return point.add((normVector.div(MatrixMath.magnitude(normVector))).mult(dist(line,point)));
    }

}
