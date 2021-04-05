precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
uniform vec2                iMouse; 
 
/*
	Blurring by scattering pixels

	Based on https://github.com/FlexMonkey/Filterpedia/blob/7a0d4a7070894eb77b9d1831f689f9d8765c12ca/Filterpedia/customFilters/Scatter.swift

	Simon Gladman | November 2017 | http://flexmonkey.blogspot.co.uk
*/

float noise(vec2 co) {
    vec2 seed = vec2(sin(co.x), cos(co.y));
    return fract(sin(dot(seed ,vec2(12.9898,78.233))) * 43758.5453);
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	float radius = abs(25.0 *intensity/100.0);

    vec2 offset = -radius + vec2(noise(fragCoord), noise(fragCoord.yx)) * radius * 2.0; 
    
    vec2 uv = (fragCoord + offset ) / iResolution.xy;
    
	fragColor = texture2D(iChannel0, uv);

    // vec2 uvori = fragCoord.xy / iResolution.xy;
    // vec4 texori = texture2D(iChannel0, uvori);
    // fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}