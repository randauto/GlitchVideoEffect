precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;

	vec3 orig = texture2D(iChannel0, uv).xyz;	// ...this is the video

	vec3 col = orig * orig * 1.4; 				// ...some contrast
	float bri = (col.x+col.y+col.z)/3.0;		// ...it's ok it's black and white!

	float v = smoothstep(.0, .7, bri);			// ...tint the dark values into cyan/teal.
	col = mix(vec3(0., 1., 1.2) * bri, col, v);

	v = smoothstep(.2, 1.1, bri);				// ...tint the light values into orange.
	col = mix(col, min(vec3(1.0, .55, 0.) * col * 1.2, 1.0), v);

	fragColor = vec4(col,1.0);

	vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}