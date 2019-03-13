#version 320 es
layout (location = 0) in vec4 position;
layout (location = 1) in vec4 colour;
layout (location = 2) in vec2 texture;

uniform mat4 Transformation;
uniform vec2 Shear;

out vec4 ourColour;
out vec2 texCoord;

void main() {
    gl_Position = Transformation * position;
    ourColour = colour;
    texCoord = Shear * texture + 0.5 * (vec2(1.0, 1.0) - Shear);
}
