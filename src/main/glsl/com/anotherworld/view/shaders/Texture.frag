#version 120

//precision mediump float;
//varying vec4 outColour;
//varying vec4 vertexColour;
//varying vec4 gl_Color;

uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, gl_TexCoord[0].st);
    //gl_FragColor = gl_Color;
}
