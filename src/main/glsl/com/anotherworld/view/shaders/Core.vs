#version 130

//precision mediump float;
in vec4 position;
in vec4 colour;
out vec4 vertexColour;

void main() {
    gl_Position = position;
    vertexColour = colour;
}
