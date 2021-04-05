precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float motion;
const float angle = 20.0;
void main()
{
    vec2 coord;
    coord.x = textureCoordinate.x + 0.01 * sin(motion + textureCoordinate.x * angle);
    coord.y = textureCoordinate.y + 0.01 * sin(motion + textureCoordinate.y * angle);
    gl_FragColor = texture2D(sTexture, coord);
}