package com.anotherworld.view.data;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class TexturedDisplayObjectBuffersTest {

    @Test
    public void construtor_SetsValues_IsMinusOne() {
        TexturedDisplayObjectBuffers a = new TexturedDisplayObjectBuffers();
        assertThat(a.getTextureId(), is(equalTo(Optional.empty())));
    }

    @Test
    public void setVertexArrayObjectId_SetsValues_IsSetValue() {
        TexturedDisplayObjectBuffers a = new TexturedDisplayObjectBuffers();
        assertThat(a.getTextureId(), is(equalTo(Optional.empty())));
        a.setTextureId(120);
        assertThat(a.getTextureId().get(), is(equalTo(120)));
    }
    
}
