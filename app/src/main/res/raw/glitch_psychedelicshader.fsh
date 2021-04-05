
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;
 uniform sampler2D iChannel1;

 uniform  float iTime;

 uniform  vec3 iResolution;
 uniform  vec2 iChannelResolution;
uniform float               intensity;
void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = textureCoordinate;
    vec2 uv2 = textureCoordinate;
    vec2 uv3 = textureCoordinate;
    vec2 ouv = uv;

    float fT = iTime;
    float amplitude = 0.9 + 0.2 * sin(fT);

	uv.x += sin(ouv.y*9.161 + fT)*0.003 * amplitude;
    uv.y += sin(ouv.x*6.363 + fT)*0.003 * amplitude;

    uv2.x -= sin(ouv.y*9.861 + fT)*0.003 * amplitude;
    uv2.y -= sin(ouv.x*7.395 + fT)*0.003 * amplitude;

    uv3.x += sin(ouv.y*15.161 + fT)*0.02;
    uv3.y -= sin(ouv.x*9.363 + fT)*0.02;

    float am = ((texture(iChannel1, mix(uv2, uv3, 0.5 + 0.5 * sin(iTime)) * 0.1 + vec2(iTime * 0.01, iTime * 0.02)).r) * 1. ) +
        ((texture(iChannel1, mix(uv2, uv3, 0.5 + 0.5 * sin(iTime + 121.)) * 0.2).r) * 0.5) +
        ((texture(iChannel1, mix(uv2, uv3, 0.5 + 0.5 * sin(iTime - 1241.51)) * 0.4).r) * 0.25) +
        ((texture(iChannel1, mix(uv2, uv3, 0.5 + 0.5 * sin(iTime + 115.)) * 0.8).r) * 0.125) +
        ((texture(iChannel1, mix(uv, uv3, 0.5 + 0.5 * sin(iTime - 74.61)) * 1.6).r) * 0.0625);

    am *= 0.5;

    am = pow(am, 2.) * 1.4;

    vec3 col = mix(texture(iChannel0, uv).rgb, texture(iChannel0, uv2).rgb, am);

    fragColor = vec4(col,1.0);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}
