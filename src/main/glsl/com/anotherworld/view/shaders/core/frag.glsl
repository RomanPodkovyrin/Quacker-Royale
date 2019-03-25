#version 120

varying vec4 ourColour;
varying vec2 texCoord;

uniform sampler2D tex;
uniform bool hasTex;
uniform mat4 textureTransformation;

void main() {
    if (hasTex) {
        vec4 tempCoord = textureTransformation * vec4(texCoord, 0, 1);
        gl_FragColor = ourColour * texture2D(tex, vec2(tempCoord[0], tempCoord[1]));
    } else {
        gl_FragColor = ourColour;
    }
}
