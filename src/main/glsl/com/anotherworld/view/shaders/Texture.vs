#version 120

attribute vec4 position;
attribute vec4 colour;
attribute vec2 texture;

//TODO IMPLEMENT THIS
//uniform vec4 gl_ModelViewProjectionMatrix

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * position;
    gl_FrontColor = colour;
    gl_TexCoord[0] = vec4(texture, 0, 1);
}
