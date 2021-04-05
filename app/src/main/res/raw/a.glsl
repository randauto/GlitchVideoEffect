#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 textureCoordinate;
uniform samplerExternalOES inputImageOESTexture;

void main() {
    vec4 color = texture2D(inputImageOESTexture, textureCoordinate);
    if (color.r < 0.5 && color.g < 0.5 && color.b < 0.5){
        color = vec4(0, 0, 0, 0);
    }
    gl_FragColor = color;
}