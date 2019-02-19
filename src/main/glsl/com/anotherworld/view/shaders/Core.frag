#version 130

precision mediump float;
in vec4 vertexColour;
out vec4 outColour;

void main() {
    outColour = /*vec4(1.0, 1.0, 1.0, 1.0);*/vertexColour;
}
