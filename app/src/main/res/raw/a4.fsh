#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;

void mainImage(out vec4 fragColor, in vec2 c)
{
    vec2 p = c.xy / iResolution.xy;
    vec4 col = texture2D(sTexture, p+(texture2D(sTexture, p).rg)*1.1);
    if (texture2D(sTexture, p).r>.5){ // strong enough to loose the eyes
        fragColor = col;
    }
}
void main() {
    mainImage(gl_FragColor, textureCoordinate.xy*iResolution.xy);
}



