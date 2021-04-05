
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float  intensity;
void main()
{
    vec2 i = textureCoordinate;
    vec4 o = vec4(0.);
    o = texture(iChannel0,i.x<.5 ? i : vec2(1.-i.x,i.y));
    o = i.x>.5 ? vec4(.2*o.r + .7*o.g + .1*o.b) : o;
    fragColor = o;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

