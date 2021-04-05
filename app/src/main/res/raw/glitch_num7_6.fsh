
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
vec3 thermal_vision(in vec3 color) {

	vec3 c_r = vec3(0.0, 0.0, 1.0);
	vec3 c_g = vec3(1.0, 1.0, 0.0);
	vec3 c_b = vec3(1.0, 0.0, 0.0);

    float lum = dot(vec3(0.20, 0.3, 0.25), color);
    if(lum < 0.5) {
    	color = mix(c_r, c_g, lum / 0.5);
    } else {
    	color = mix(c_g, c_b, (lum - 0.5) / 0.5);
    }
    return color;
}

void main() {
    vec2 uv = textureCoordinate;
    vec3 color = texture(iChannel0, uv).rgb;
    //for color inversion comment:
    //color.rgb = 1.0 - color.rgb;
    fragColor = vec4(thermal_vision(color), 1.0);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


