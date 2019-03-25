#version 120
attribute vec4 position;
attribute vec4 colour;
attribute vec2 texture;

uniform mat4 Transformation;
uniform vec2 Shear;

varying vec4 ourColour;
varying vec2 texCoord;

void main() {
    gl_Position = Transformation * position;
    ourColour = colour;
    texCoord = Shear * texture + 0.5 * (vec2(1.0, 1.0) - Shear);
}
