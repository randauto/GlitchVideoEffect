precision highp float;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec4 mask = texture2D(sTexture, fragCoord);
    vec4 color =vec4(1.0-mask.r, 1.0-mask.g, 1.0-mask.r,1.0);
    fragColor = color;
}

void main() {
	mainImage(gl_FragColor, textureCoordinate);
}