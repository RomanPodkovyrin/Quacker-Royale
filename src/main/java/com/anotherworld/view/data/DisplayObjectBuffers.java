package com.anotherworld.view.data;

public class DisplayObjectBuffers {
    
    private int verticesId;

    private int edgesId;

    private int vaoId;
    
    private int colourId;
    
    public DisplayObjectBuffers() {
        verticesId = -1;
        edgesId = -1;
        vaoId = -1;
        colourId = -1;
    }
    
    public void setVertexArrayObjectId(int id) {
        this.vaoId = id;
    }
    
    public int getVertexArrayObjectId() {
        return this.vaoId;
    }
    
    public void setVerticesId(int id) {
        this.verticesId = id;
    }
    
    public int getVerticesId() {
        return this.verticesId;
    }
    
    public void setEdgesId(int id) {
        this.edgesId = id;
    }
    
    public int getEdgesId() {
        return this.edgesId;
    }
    
    public void setColourId(int id) {
        this.colourId = id;
    }
    
    public int getColourId() {
        return this.colourId;
    }
    
}
