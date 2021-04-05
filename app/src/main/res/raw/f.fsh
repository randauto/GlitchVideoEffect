precision highp float;

uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
  {
    vec4 color = texture2D(sTexture, fragCoord);
    float newR = abs(color.r + color.g * 2.0 - color.b) * color.r;
    float newG = abs(color.r + color.b * 2.0 - color.g) * color.r;
    float newB = abs(color.r + color.b * 2.0 - color.g) * color.g;
	vec4 newColor = vec4(newR, newG, newB, 1.0);
	fragColor = newColor;
   }

void main() {
 	mainImage(gl_FragColor, textureCoordinate);
 }