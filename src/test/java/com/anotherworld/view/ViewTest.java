package com.anotherworld.view;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

import java.util.ArrayList;

import org.junit.Test;

public class ViewTest {
    
    @Test
    public void view_None_Runs() throws InterruptedException {
        View view = new View(192, 108);
        Thread thread = new Thread(view);
        thread.start();
        Thread.sleep(5000);
        view.close();
        thread.join();
    }
    
    @Test
    public void view_TestPlatform_Runs() throws InterruptedException {
        View view = new View(192, 108);
        Thread thread = new Thread(view);
        thread.start();
        ArrayList<RectangleDisplayData> platform = new ArrayList<>();
        platform.add(new RectangleTestDisplayData());
        view.updateGameObjects(new ArrayList<>(), new ArrayList<>(), platform, new ArrayList<>());
        Thread.sleep(15000);
        view.close();
        thread.join();
    }
    
    private class RectangleTestDisplayData implements RectangleDisplayData {

        @Override
        public float getXCoordinate() {
            return 80;
        }

        @Override
        public float getYCoordinate() {
            return 45;
        }

        @Override
        public float getAngle() {
            return 0;
        }

        @Override
        public ObjectState getState() {
            return null;
        }

        @Override
        public float getWidth() {
            return 160;
        }

        @Override
        public float getHeight() {
            return 90;
        }
        
    }
    
}
