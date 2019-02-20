#version 130

//precision mediump float;
in vec4 position;
in vec4 colour;

//TODO IMPLEMENT THIS
//uniform vec4 gl_ModelViewProjectionMatrix

out vec4 vertexColour;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * position;
    vertexColour = colour;
}
