#extension GL_OES_EGL_image_external : require

precision mediump float;

uniform samplerExternalOES camTexture;
//necessary

varying vec2 v_CamTexCoordinate;
varying vec2 v_TexCoordinate;
//end necessary

// Code taken from https://www.shadertoy.com/view/XsfGzj and modified to work in this context as an example
// of how quickly and easily you can get up and running with shadercam

// passed in via our SuperAwesomeRenderer.java
uniform float	iGlobalTime;

// play around with xy for different sized effect, or pass in via GLES20.glUniform3f();
uniform vec3    iResolution;


void main ()
{
    vec2 uv = v_CamTexCoordinate.xy / iResolution.xy;
    vec4 texColor = texture2D(camTexture, uv);
    float col = (texColor.r + texColor.g + texColor.b) / 3.0;
    gl_FragColor = vec4(col, col, col, 1.0);
}
