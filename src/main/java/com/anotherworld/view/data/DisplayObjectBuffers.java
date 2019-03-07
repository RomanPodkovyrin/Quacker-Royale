package com.anotherworld.view.data;

/**
 * Creates the opengl buffers for a displayobject.
 */
public class DisplayObjectBuffers {
    
    private int verticesId;

    private int edgesId;

    private int vaoId;
    
    private int colourId;

    /**
     * Initialises the buffer values to unknown.
     */
    public DisplayObjectBuffers() {
        verticesId = -1;
        edgesId = -1;
        vaoId = -1;
        colourId = -1;
    }
    
    /**
     * Assigns a vertexArrayObjectId for the object.
     * @param id the vertexArrayObjectId
     */
    public void setVertexArrayObjectId(int id) {
        this.vaoId = id;
    }
    
    /**
     * Gets the id of the object's vertexArrayObject for the object.
     * @return the vertex arrayObject Id
     */
    public int getVertexArrayObjectId() {
        return this.vaoId;
    }
    
    /**
     * Assigns the vertices buffer id for the object.
     * @param id the buffer id
     */
    public void setVerticesId(int id) {
        this.verticesId = id;
    }
    
    /**
     * Returns the vertices buffer id for the object.
     * @return the vertices buffer id
     */
    public int getVerticesId() {
        return this.verticesId;
    }
    
    /**
     * Assigns the edge buffer id for the object.
     * @param id the edge buffer id
     */
    public void setEdgesId(int id) {
        this.edgesId = id;
    }
    
    /**
     * Returns the edge buffer id for the object.
     * @return the edge buffer id
     */
    public int getEdgesId() {
        return this.edgesId;
    }
    
    /**
     * Assigns the buffer colour id for the object.
     * @param id the buffer colour id
     */
    public void setColourId(int id) {
        this.colourId = id;
    }
    
    /**
     * Returns the colour buffer id for the object.
     * @return the colour buffer id
     */
    public int getColourId() {
        return this.colourId;
    }
    
}
