precision highp float;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec4 mask = texture2D(sTexture, fragCoord);
    vec4 tempColor = vec4(0.393 * mask.r + 0.769 * mask.g + 0.189 * mask.b,
     0.349 * mask.r + 0.686 * mask.g + 0.168 * mask.b,
      0.272 * mask.r + 0.534 * mask.g + 0.131 * mask.b, 1.0);
    fragColor = tempColor;
}

void main() {
	mainImage(gl_FragColor, textureCoordinate);
}