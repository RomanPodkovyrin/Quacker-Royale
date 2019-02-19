#version 320 es

precision mediump float;
in vec4 position;
//out vec4 outColour;

void main() {
    //outColour = vec4(1.0, 1.0, 1.0, 1.0);
    gl_Position = position;
}
