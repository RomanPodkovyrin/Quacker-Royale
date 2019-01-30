package com.anotherworld.model.ai.aiMathsTools;

public class Line {
    private Matrix point;
    private float s;
    private Matrix vector;

    //Line representation: a*x1 + b*x2 = d
    private float a;
    private float x1;
    private float b;
    private float x2;
    private float d;

    public Matrix getPoint() {
        return point;
    }

    public float getA() {
        return a;
    }

    public float getX1() {
        return x1;
    }

    public float getB() {
        return b;
    }

    public float getX2() {
        return x2;
    }

    public float getD() {
        return d;
    }

    //Takes P + s V
    public Line(Matrix point, float s, Matrix vector){
        this.point = point;
        this.s = s;
        this.vector = vector;

        this.a = - this.vector.getY();
        this.b = this.vector.getX();
        this.d = (this.a * this.point.getX()) + (this.b * this.point.getY());

    }
}
