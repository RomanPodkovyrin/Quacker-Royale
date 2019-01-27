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
		
		while(!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			Matrix2d rotation = Matrix2d.ROTATION_2D((float)glfwGetTime() % 360);
			
			drawMatrix(rotation.mult(Matrix2d.TEST_SQUARE()));
			
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
			glVertex2f(a.getValue(0, j), a.getValue(1, j));
		}
		System.out.println();
		glEnd();
	}
	
	public static void main(String args[]) {
		(new ViewPrototype()).start();
	}
	
}
