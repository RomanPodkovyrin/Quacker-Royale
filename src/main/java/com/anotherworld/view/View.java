package com.anotherworld.view;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class View {
	
	private Long window;
	
	private DisplayObject[] objects;
	
	public View() {
		objects = new DisplayObject[10];
		for(int i = 0; i < 5; i++) {
			objects[i] = (new Ball((float)Math.random() * 160, (float)Math.random() * 90, (float)Math.random() * 360));
			objects[i + 5] = (new Player((float)Math.random() * 160, (float)Math.random() * 90, 10f));
		}
		this.start();
	}
	
	public void display(DisplayObject[] players, DisplayObject[] balls, DisplayObject[] platform, DisplayObject[] wall) {
		synchronized(objects) {
			objects = new DisplayObject[players.length + balls.length + platform.length + wall.length];
			int index = 0;
			for (int i = 0; i < platform.length; i++) {
				objects[i] = platform[i];
			}
			index += platform.length;
			for (int i = 0; i < balls.length; i++) {
				objects[i + index] = balls[i];
			}
			index += balls.length;
			for (int i = 0; i < players.length; i++) {
				objects[i + index] = players[i];
			}
			index += players.length;
			for (int i = 0; i < wall.length; i++) {
				objects[i + index] = wall[i];
			}
			index += wall.length;
		}
	}
	
	public void close() {
		
	}
	
	private void start() {
		if (!glfwInit()) {
			throw new IllegalStateException("Couldn't initialise glfw");
		}
		
		window = glfwCreateWindow(1120, 630, "Bullet Hell", NULL, NULL);
		
		if (window == null) {
			glfwTerminate();
			throw new RuntimeException("Couldn't create glfw window");
		}
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		while(!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			Matrix2d viewMatrix = calculateViewMatrix(0, 0, 160, 90, 0, 0);
			
			for (DisplayObject obj : objects) {
				drawObject(obj, viewMatrix);
			}
			
			glFlush();
			
			glfwSwapBuffers(window);
			
			glfwPollEvents();
			
		}
		
		glfwTerminate();
	}
	
	private void drawMatrix(Matrix2d a) {
		glBegin(GL_POLYGON);
		for (int j = 0; j < a.getN(); j++) {
			glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
		}
		System.out.println();
		glEnd();
	}
	
	private Matrix2d calculateViewMatrix(float camX, float camY, float camW, float camH, float xRes, float yRes) {
		Matrix2d modifier;

		modifier = (Matrix2d.H_TRANSLATION_2D(-1f, 1f));
		
		modifier = modifier.mult(Matrix2d.H_SCALE_2D(1f, -1f));
		
		modifier = modifier.mult(Matrix2d.H_SCALE_2D(2 / camW, 2 / camH));
		
		modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(-camX, -camY));
		
		return modifier;
	}
	
	private void drawObject(DisplayObject obj, Matrix2d viewMatrix) {
		Matrix2d modifier = viewMatrix;
		
		modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(obj.getX(), obj.getY()));
		
		modifier = modifier.mult(Matrix2d.H_ROTATION_2D(obj.getTheta()));
		
		drawMatrix(modifier.mult(obj.getPoints()));
		
	}
	
	@Deprecated
	public static void main(String args[]) {
		new View();
	}
	
}
