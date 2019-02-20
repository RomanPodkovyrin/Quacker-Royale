#version 120

//precision mediump float;
attribute vec4 position;
attribute vec4 colour;

//TODO IMPLEMENT THIS
//uniform vec4 gl_ModelViewProjectionMatrix

//varying vec4 gl_FrontColor;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * position;
    gl_FrontColor = colour;
}
