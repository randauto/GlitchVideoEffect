precision highp float;

uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec4 mask = texture2D(sTexture, fragCoord);
    float color = (mask.r + mask.g + mask.b) /3.0;
    vec4 tempColor =vec4(color, color, color,1.0);
    fragColor = tempColor;
}

void main() {
	mainImage(gl_FragColor, textureCoordinate);
}