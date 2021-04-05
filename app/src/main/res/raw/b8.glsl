//#version 300 es
attribute vec2  aPosition;
attribute vec2  aTextureCoord;
varying vec2    textureCoordinate;

void main() {
    textureCoordinate = aTextureCoord;
    gl_Position = vec4 ( aPosition.x, aPosition.y, 0.0, 1.0 );
}