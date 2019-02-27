package com.anotherworld.view;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;

import org.junit.Test;
import org.lwjgl.opengl.GL;

public class TextureMapTest {

    /**
     * Creates a test window that allows opengl and glfw calls.
     */
    public long createTestWindow() {
        if (!glfwInit()) {
            throw new IllegalStateException("Couldn't initialise glfw");
        }
        
        Long window = glfwCreateWindow(100, 100, "Bullet Hell", NULL, NULL);
        
        if (window.equals(null)) {
            glfwTerminate();
            throw new RuntimeException("Couldn't create glfw window");
        }

        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        
        return window;
    }
    
    /**
     * Destroys the created test window.
     */
    public void destroyTestWindow() {
        glfwTerminate();
    }
    
    @Test
    public void load_loadAlien_isLoaded() throws IOException {
        createTestWindow();
        TextureMap tm = new TextureMap("res/images/alien.png");
        assertThat(tm.getHeight(), is(96));
        assertThat(tm.getWidth(), is(96));
        assertThat(tm.getPixels().capacity(), is(greaterThanOrEqualTo(96 * 96 * 4)));
        System.out.println(tm.toString());
        destroyTestWindow();
    }
    
}
