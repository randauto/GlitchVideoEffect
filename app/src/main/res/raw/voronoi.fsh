precision highp float;

uniform int                 iFrame;
uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
uniform sampler2D           iChannel1;
varying vec2                texCoord;
uniform float               intensity;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;

#if 0 // debug feature extraction

    fragColor = texture2D(iChannel1, uv).wwww;

#else

	vec4 cell = texture2D(iChannel0, uv);
    vec2 cell_uv = cell.xy;
    vec4 video = texture2D(iChannel1, cell_uv);
    vec2 dcell = cell_uv * iResolution.xy - fragCoord.xy;
    float len = length(dcell);
    vec3 color = video.xyz * (.9 + len*.005);
    fragColor = vec4(color, 1.);

    vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
#endif
}

void main() {
	mainImage(gl_FragColor, texCoord*iResolution.xy);
}