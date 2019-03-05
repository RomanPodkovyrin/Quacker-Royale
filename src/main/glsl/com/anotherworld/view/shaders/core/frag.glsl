#version 320 es
precision mediump float;
out vec4 FragColour;

in vec4 ourColour;
in vec2 texCoord;

uniform sampler2D tex;

void main() {
    FragColour = ourColour;
}
