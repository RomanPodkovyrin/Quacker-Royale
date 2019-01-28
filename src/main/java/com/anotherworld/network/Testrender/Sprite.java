package com.anotherworld.network.Testrender;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Sprite {

    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double velocity;
    private double width;
    private double height;
    private double angle; // angle form North, clockwise
    // 0/360 is North, 90 is East, 180 is South and 270 is West

    public Sprite(Image image, double y,double x, double height, double width){
        this.image = image;

        this.positionY = y;
        this.positionX = x;

        this.velocityX = 0;
        this.velocityY = 0;
        this.velocity = 170;

        this.width = width;
        this.height = height;

        this.angle = 0;
    }

    public void rotate(double angle){
            this.angle = (this.angle + angle) % 360 ;
    }
    public void stop(){
        this.velocityY = 0;
        this.velocityX = 0;
        //this.velocity = 0;
    }

    public void go(){
        // calculate the x and y velocity based on the current angel
        this.velocityY = Math.sin(angle) * velocity;
        this.velocityX = Math.cos(angle) * velocity;
    }

    public void setImage(String image){
        this.image = new Image(new File(image).toURI().toString());
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public void update(double time ){
        this.positionX += velocityX * time;
        this.positionY += velocityY * time;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image,positionX,positionY);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
