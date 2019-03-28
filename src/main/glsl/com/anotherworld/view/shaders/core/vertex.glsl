#version 120
attribute vec4 position;
attribute vec4 colour;
attribute vec2 texture;

uniform mat4 Transformation;

varying vec4 ourColour;
varying vec2 texCoord;

void main() {
    gl_Position = Transformation * position;
    ourColour = colour;
    texCoord = texture;
}
