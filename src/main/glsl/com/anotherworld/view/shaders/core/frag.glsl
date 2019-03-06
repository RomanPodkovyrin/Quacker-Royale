#version 300 es
precision mediump float;
out vec4 FragColour;

in vec4 ourColour;

void main() {
    FragColour = ourColour;
}
