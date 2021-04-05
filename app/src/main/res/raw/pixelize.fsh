precision highp float;

uniform vec3                iResolution;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
#define S (iResolution.x / 6e1) // The cell size.

void mainImage(out vec4 fragColor, vec2 p)
{
    fragColor = texture2D(iChannel0, floor((p + .5) / S) * S / iResolution.xy);
    
    vec2 uvori = p.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
	mainImage(gl_FragColor, texCoord*iResolution.xy);
}