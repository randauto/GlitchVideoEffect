precision mediump float;
attribute vec4 aPosition;
attribute vec4 aTextureCoord;
varying vec2 textureCoordinate;
varying vec2 coords[8];
uniform vec2 samplerSteps;
const float stride = 2.0;

void main() {
    gl_Position =  aPosition;
    textureCoordinate = aTextureCoord.xy;
    coords[0] = textureCoordinate - samplerSteps * stride;
    coords[1] = textureCoordinate + vec2(0.0, -samplerSteps.y) * stride;
    coords[2] = textureCoordinate + vec2(samplerSteps.x, -samplerSteps.y) * stride;
    coords[3] = textureCoordinate - vec2(samplerSteps.x, 0.0) * stride;
    coords[4] = textureCoordinate + vec2(samplerSteps.x, 0.0) * stride;
    coords[5] = textureCoordinate + vec2(-samplerSteps.x, samplerSteps.y) * stride;
    coords[6] = textureCoordinate + vec2(0.0, samplerSteps.y) * stride;
    coords[7] = textureCoordinate + vec2(samplerSteps.x, samplerSteps.y) * stride;
}