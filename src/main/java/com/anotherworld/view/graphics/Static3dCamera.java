package com.anotherworld.view.graphics;

import com.anotherworld.view.data.Matrix2d;
import com.anotherworld.view.data.Points2d;

public class Static3dCamera extends Static2dCamera {
    
    public Static3dCamera(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Applies matrix transformations using the camera's position so it is centred on the screen.
     * @param camera The camera to use for display
     * */
    @Override
    public Matrix2d transform() {
        Matrix2d transformation = this.cameraProjectionf(this.getDepth());
        transformation = transformation.mult(Matrix2d.homScale3d(1 / this.getWidth(), -1 / this.getHeight(), 0));
        transformation = transformation.mult(this.cameraRotation());
        transformation = transformation.mult(Matrix2d.homTranslate3d(-this.getX(), -this.getY(), -this.getZ()));
        return transformation;
    }
    
    private Matrix2d cameraRotation() {
        Points2d n = this.getViewDirection().normalise();
        Points2d v = this.getHandedness().normalise();
        Points2d u = this.getUpDirection().normalise();
        Matrix2d rotation = new Matrix2d(4, 4);
        for (int i = 0; i < 3; i++) {
            rotation.setValue(0, i, v.getValue(i, 0));
            rotation.setValue(1, i, u.getValue(i, 0));
            rotation.setValue(2, i, n.getValue(i, 0));
        }
        rotation.setValue(3, 3, 1f);
        return (rotation);
    }
    
    @Override
    public float getY() {
        return super.getY() * 2;
    }

    @Override
    public float getZ() {
        return -super.getY();
    }

    @Override
    public float getDepth() { //Don't ask
        return 1.414213562f;
    }

    @Override
    public Points2d getViewDirection() {
        Points2d tempUp = new Points2d(3, 1);
        tempUp.setValue(1, 0, -1f);
        tempUp.setValue(2, 0, 1f);
        return tempUp;
    }

}
