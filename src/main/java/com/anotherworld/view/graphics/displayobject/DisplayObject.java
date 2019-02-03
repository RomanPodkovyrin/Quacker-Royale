package com.anotherworld.view.graphics.displayobject;

import com.anotherworld.view.graphics.Matrix2d;

public class DisplayObject {

    private float theta;
    private float x;
    private float y;
    private Matrix2d points;

    public DisplayObject(Matrix2d points, float x, float y, float theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.points = points;
    }

    public Matrix2d getPoints() {
        return points;
    }

    public float getX() {
        return x;
    }

    @Deprecated
    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public float getTheta() {
        return theta;
    }

}
