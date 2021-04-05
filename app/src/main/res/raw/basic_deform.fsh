precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{	
	float stongth = 0.3;
	vec2 uv = fragCoord.xy;
	float waveu = sin((uv.y + iTime) * 20.0) * 0.5 * 0.05 * stongth;
	fragColor = texture2D(iChannel0, uv + vec2(waveu, 0));

	vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
	mainImage(gl_FragColor, texCoord);
}