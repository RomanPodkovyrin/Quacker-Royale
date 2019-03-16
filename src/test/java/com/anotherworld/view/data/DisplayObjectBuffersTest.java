package com.anotherworld.view.data;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DisplayObjectBuffersTest {

    @Test
    public void construtor_SetsValues_IsMinusOne() {
        DisplayObjectBuffers a = new DisplayObjectBuffers();
        assertThat(a.getVertexArrayObjectId(), is(equalTo(-1)));
        assertThat(a.getColourId(), is(equalTo(-1)));
        assertThat(a.getEdgesId(), is(equalTo(-1)));
        assertThat(a.getVerticesId(), is(equalTo(-1)));
    }

    @Test
    public void setVertexArrayObjectId_SetsValues_IsSetValue() {
        DisplayObjectBuffers a = new DisplayObjectBuffers();
        assertThat(a.getVertexArrayObjectId(), is(equalTo(-1)));
        a.setVertexArrayObjectId(120);
        assertThat(a.getVertexArrayObjectId(), is(equalTo(120)));
    }

    @Test
    public void setColourId_SetsValues_IsSetValue() {
        DisplayObjectBuffers a = new DisplayObjectBuffers();
        assertThat(a.getColourId(), is(equalTo(-1)));
        a.setColourId(120);
        assertThat(a.getColourId(), is(equalTo(120)));
    }

    @Test
    public void setEdgesId_SetsValues_IsSetValue() {
        DisplayObjectBuffers a = new DisplayObjectBuffers();
        assertThat(a.getEdgesId(), is(equalTo(-1)));
        a.setEdgesId(120);
        assertThat(a.getEdgesId(), is(equalTo(120)));
    }

    @Test
    public void setVerticesId_SetsValues_IsSetValue() {
        DisplayObjectBuffers a = new DisplayObjectBuffers();
        assertThat(a.getVerticesId(), is(equalTo(-1)));
        a.setVerticesId(120);
        assertThat(a.getVerticesId(), is(equalTo(120)));
    }
    
}
