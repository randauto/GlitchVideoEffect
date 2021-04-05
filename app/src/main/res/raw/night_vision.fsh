precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
 float hash(in float n) { return fract(sin(n)*43758.5453123); }

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 p = fragCoord.xy / iResolution.xy;

	vec2 u = p * 2. - 1.;
	vec2 n = u * vec2(iResolution.x / iResolution.y, 1.0);
	vec3 c = texture2D(iChannel0, p).xyz;

	float curtime = iTime + 2.;
	// flicker, grain, vignette, fade in
	c += sin(hash(curtime)) * 0.01;
	c += hash((hash(n.x) + n.y) * curtime) * 0.5;
	c *= smoothstep(length(n * n * n * vec2(0.075, 0.4)), 1.0, 0.4);
    c *= smoothstep(0.001, 3.5, curtime) * 1.5;

	c = dot(c, vec3(0.2126, 0.7152, 0.0722))
	  * vec3(0.2, 1.5 - hash(curtime) * 0.1,0.4);

	fragColor = vec4(c,1.0);

	vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}



void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}