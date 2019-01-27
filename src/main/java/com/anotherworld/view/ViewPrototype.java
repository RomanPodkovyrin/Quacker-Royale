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
		
		window = glfwCreateWindow(640, 280, "Bullet Hell", NULL, NULL);
		
		if (window == null) {
			glfwTerminate();
			throw new RuntimeException("Couldn't create glfw window");
		}
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		while(!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			glfwSwapBuffers(window);
			
			glfwPollEvents();
			
		}
		
		glfwTerminate();
	}
	
	public static void main(String args[]) {
		(new ViewPrototype()).start();
	}
	
}
