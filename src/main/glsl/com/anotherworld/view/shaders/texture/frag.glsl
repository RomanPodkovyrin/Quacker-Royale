#version 320 es
precision mediump float;
out vec4 FragColour;

in vec4 ourColour;
in vec2 texCoord;

uniform sampler2D tex;
uniform bool hasTex;
uniform mat4 textureTransformation;

void main() {
    if (hasTex) {
        vec4 tempCoord = textureTransformation * vec4(texCoord, 0, 1);
        FragColour = ourColour * texture(tex, vec2(tempCoord[0], tempCoord[1]));
    } else {
        FragColour = ourColour;
    }
}
