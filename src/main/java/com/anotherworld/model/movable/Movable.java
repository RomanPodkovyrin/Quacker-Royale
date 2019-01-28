package main.java.com.anotherworld.model.movable;

public class Movable {
    private float xCoordinate;
    private float yCoordinate;
    private ObjectStates state;
    private float angle;
    private float xVelocity;
    private float yVelocity;
    private float speed;

    private float radious;

    public Movable(float xCoordinate, float yCoordinate,ObjectStates state){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.state = state;

    }
}
