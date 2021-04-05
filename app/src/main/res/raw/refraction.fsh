precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
uniform sampler2D           iChannel1;
varying vec2                texCoord;
uniform float               intensity;
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy;

	vec4 bump = texture2D(iChannel1, uv + iTime * 0.05);

	vec2 vScale = vec2 (0.01, 0.01);
	vec2 newUV = uv + bump.xy * vScale.xy;

	vec4 col = texture2D(iChannel0, newUV);

	fragColor = vec4(col.rgb, 1.0);

    vec4 texori = texture2D(iChannel0, uv);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
	mainImage(gl_FragColor, texCoord);
}