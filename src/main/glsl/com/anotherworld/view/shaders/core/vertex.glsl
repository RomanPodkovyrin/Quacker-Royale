#version 300 es
layout (location = 0) in vec4 position;
layout (location = 1) in vec4 colour;
layout (location = 2) in vec2 texture;

uniform mat4 Transformation;

out vec4 ourColour;
out vec2 texCoord;

void main() {
    gl_Position = /*gl_ModelViewProjectionMatrix */ Transformation * position;
    ourColour = colour;
    texCoord = texture;
}
