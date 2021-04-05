precision highp float;

uniform vec3                iResolution;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec4 mask = texture2D(sTexture, fragCoord);
    vec4 tempColor = vec4(mask.r*0.5/(mask.g + mask.b + 0.01),
     mask.g*0.5/(mask.r + mask.b + 0.01),
     mask.b*0.5/(mask.r + mask.g + 0.01),
       1.0);
    fragColor = tempColor;
}

void main() {
 	mainImage(gl_FragColor, textureCoordinate);
 }