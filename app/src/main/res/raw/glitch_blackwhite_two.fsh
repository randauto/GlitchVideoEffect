precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

 uniform vec3 iResolution;
uniform float               intensity;
#define invert_color

float rand(float x)
{
    return fract(sin(x) * 43758.5453);
}

float triangle(float x)
{
	return abs(1.0 - mod(abs(x), 2.0)) * 2.0 - 1.0;
}

void main()
{
    float time = floor((iTime + 10.) * 16.0) / 16.0;

    vec2 uv = textureCoordinate;


    // pixel position
	vec2 p = uv;
	p += vec2(triangle(p.y * rand(time) * 4.0) * rand(time * 1.9) * 0.015,
			triangle(p.x * rand(time * 3.4) * 4.0) * rand(time * 2.1) * 0.015);
	p += vec2(rand(p.x * 3.1 + p.y * 8.7) * 0.01,
			  rand(p.x * 1.1 + p.y * 6.7) * 0.01);


    #ifdef distort_all
    vec2 blurredUV = vec2(p.x+0.003,p.y+0.003);
    vec4 baseColor = vec4(texture2D(iChannel0, blurredUV).rgb,1.);
    #else
    vec4 baseColor = vec4(texture2D(iChannel0, uv).rgb,1.);
    #endif


	vec4 edges = 1.0 - (baseColor / vec4(texture2D(iChannel0,p).rgb, 1.));

    #ifdef invert_color
    baseColor.rgb = vec3(baseColor.r);
    gl_FragColor = baseColor / vec4(length(edges));
    #else
	gl_FragColor = vec4(length(edges));
    #endif

     vec4 texori = texture2D(iChannel0, uv);
    gl_FragColor = vec4(texori.rgb*(1.0-intensity) + gl_FragColor.rgb*intensity,1.);
}


