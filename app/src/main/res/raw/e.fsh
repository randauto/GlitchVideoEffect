precision mediump float;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy;
	
	vec3 tex = texture2D( sTexture, uv ).rgb;
	float shade = dot(tex, vec3(0.333333));

	vec3 col = mix(vec3(0.1, 0.36, 0.8) * (1.0-2.0*abs(shade-0.5)), vec3(1.06, 0.8, 0.55), 1.0-shade);
	
    fragColor = vec4(col,1.0);
}

void main() {
	mainImage(gl_FragColor, textureCoordinate);
}