package com.anotherworld.view;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ViewPrototype {
	
	private Long window;
	
	public ViewPrototype() {
		
	}
	
	public void start() {
		if (!glfwInit()) {
			throw new IllegalStateException("Couldn't initialise glfw");
		}
		
		window = glfwCreateWindow(600, 600, "Bullet Hell", NULL, NULL);
		
		if (window == null) {
			glfwTerminate();
			throw new RuntimeException("Couldn't create glfw window");
		}
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		DisplayObject test = new DisplayObject(Matrix2d.TEST_SQUARE(5), 90, 10, 0);
		
		while(!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			//Matrix2d rotation = Matrix2d.ROTATION_2D((float)glfwGetTime() % 360);
			
			//drawMatrix((Matrix2d.TEST_SQUARE()));
			
			Matrix2d viewMatrix = calculateViewMatrix(0, 0, 100, 100, 0, 0);
			
			drawObject(test, viewMatrix);
			
			glFlush();
			
			glfwSwapBuffers(window);
			
			glfwPollEvents();
			
		}
		
		glfwTerminate();
	}
	
	private void drawMatrix(Matrix2d a) {
		glBegin(GL_POLYGON);
		for (int j = 0; j < a.getN(); j++) {
			System.out.println(a.getValue(0, j) + ":" + a.getValue(1, j));
			glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
		}
		System.out.println();
		glEnd();
	}
	
	private Matrix2d calculateViewMatrix(float camX, float camY, float camW, float camH, float xRes, float yRes) {
		Matrix2d modifier;
		
		//modifier = (Matrix2d.H_TRANSLATION_2D(-1f, 0f));
		
		//modifier.draw("Cam center");

		modifier = /*modifier.mult*/(Matrix2d.H_TRANSLATION_2D(-1f, 1f));
		
		modifier = modifier.mult(Matrix2d.H_SCALE_2D(1f, -1f));
		
		modifier.draw("Cam invert");
		
		modifier = modifier.mult(Matrix2d.H_SCALE_2D(2 / camW, 2 / camH));
		
		modifier.draw("Cam scale");
		
		modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(-camX, -camY));
		
		modifier.draw("Cam shift");
		
		return modifier;
	}
	
	private void drawObject(DisplayObject obj, Matrix2d viewMatrix) {
		Matrix2d modifier = viewMatrix;
		
		modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(obj.getX(), obj.getY()));
		
		modifier.draw("Translation");
		
		modifier = modifier.mult(Matrix2d.H_ROTATION_2D(obj.getTheta()));

		modifier.draw("Rotation");
		
		drawMatrix(modifier.mult(obj.getPoints()));

		modifier.mult(obj.getPoints()).draw("Points");
		
	}
	
	public static void main(String args[]) {
		(new ViewPrototype()).start();
	}
	
}
