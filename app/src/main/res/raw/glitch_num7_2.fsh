
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
const float pi = 3.14159265;


vec2 rotate (vec2 coord, float angle){
    float sin_factor = sin(angle);
    float cos_factor = cos(angle);
	vec2 c = vec2((coord.x - 0.5) * (iResolution.x / iResolution.y), coord.y - 0.5) * mat2(cos_factor, sin_factor, -sin_factor, cos_factor);
    c += 0.5;
    return c;
}


void main()
{
	vec2 uv = vec2(textureCoordinate.x, 1. - textureCoordinate.y);
    uv.y = 1.0 - uv.y;

    vec2 res = iResolution.xy;

    vec2 rL = rotate(uv, radians(90.0) );
    rL.x *= res.y / res.x;
    rL.x -= (res.x / res.y);
    rL.y -= 0.446;

    vec2 rR = rotate(uv, radians(-90.0));
    rR.x *= res.y / res.x;
    //rR.x -= 0.5;
    rR.x -= (res.x / res.y);
    rR.y -= 0.446;

    vec4 tM = texture(iChannel0, uv);
	vec4 tL = texture(iChannel0, rL);
    vec4 tR = texture(iChannel0, rR);

    float lA = 1.0 -  smoothstep(0.495,0.505,(uv.x + uv.y) - 0.5);
    float rA = 1.0 - smoothstep(0.495,0.505,((1.0 - uv.x) + uv.y) - 0.5);
    float c = 1.0 - smoothstep(0.245, 0.255, uv.x);
    c += smoothstep(0.745, 0.755, uv.x);
    c = 1.0 - c;
    c += 1.0 - clamp(lA + rA, 0.0,1.0) ;
    c = clamp(c, 0.0,1.0);
    float cL = c * step(uv.x, 0.5);
    float cR = clamp(c * step(1.0 - uv.x, 0.5), 0.0,1.0);

    vec4 left = mix(tL, tM, cL);
    left *= step( uv.x, 0.5);
    vec4 right = mix(tR, tM, cR);
    right *= step(1.0 - uv.x, 0.5);
    vec4 col = vec4(vec3(left.rgb + right.rgb), 1.0);

	fragColor = col;//vec4(uv,0.5+0.5*sin(iTime),1.0);
	//fragColor.rgb = clamp(fragColor.rgb, vec3(0.0), vec3(1.0));
    //fragColor.rgb += c;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


