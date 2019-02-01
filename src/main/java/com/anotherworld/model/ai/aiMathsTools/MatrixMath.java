package com.anotherworld.model.ai.aiMathsTools;

public class MatrixMath {

    public static float innerProduct(Matrix v1 , Matrix v2){

        return  (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }

    public static float magnitude(Matrix v){
        return (float) Math.sqrt(innerProduct(v,v));
    }

    public static float dist(Line line, Matrix point){

        float d = -((line.getA() * point.getX()) + (line.getB() * point.getY()) - line.getD())/magnitude(line.getOrthogonalVector());

        return d;
    }

    public static boolean isPerpendicular(Matrix vectorLine, Matrix vectorPoint, Matrix point){
        Matrix fromVectorPoint = new Matrix(point.getX() - vectorPoint.getX(), point.getY() - vectorPoint.getY());
        //System.out.println(fromVectorPoint);
        float vectorDirectionAngle = vectorAngle(vectorLine);

        float vectorToPointAngle = vectorAngle(fromVectorPoint);
        //System.out.println(vectorToPointAngle + " " + vectorDirectionAngle);
        if (vectorToPointAngle <= vectorDirectionAngle - 90 || vectorToPointAngle >= vectorDirectionAngle + 90){
            //System.out.println("hello");
            return false;
        }
        else {
            //System.out.println(vectorToPointAngle + " " + vectorDirectionAngle);
            return true;
        }
    }

    public static float vectorAngle(Matrix vector){
        return 90 + (float) Math.toDegrees(Math.atan2(vector.getY() , vector.getX()));
    }

    public static Matrix nearestNeighbour(Line line, Matrix point){

        Matrix normVector = line.getOrthogonalVector();
        System.out.println("Hell "+(normVector.div(MatrixMath.magnitude(normVector))));

        return point.add((normVector.div(MatrixMath.magnitude(normVector))).mult(dist(line,point)));

    }

}
