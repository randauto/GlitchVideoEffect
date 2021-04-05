
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
void main()
{
	vec2 uv = textureCoordinate;
    float m = 0.5 - uv.x;

    uv.x *= (m + .5) * 3.0;

    uv.x += sin(uv.y * 10.0 + iTime*10.0)/100.0;
    uv.y += cos(uv.x * 10.0 + iTime*10.0)/100.0;

    vec4 col = texture(iChannel0, uv);
    float v = (col.r + col.g + col.b) / 3.0;
    //float v = max(col.r, col.g);
    v = max(v, col.b);
	fragColor = vec4(v, v, v, col.a);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


