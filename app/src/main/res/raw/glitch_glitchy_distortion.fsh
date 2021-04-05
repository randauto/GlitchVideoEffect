
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
	vec2 uv = textureCoordinate;

    //Noise position
    vec2 xy = uv;
    xy.x += iTime * 5.;

    //Diagonal displacement
    uv.x = uv.x - fract(uv.x + iTime * 0.5 - uv.x);
    uv.y = uv.y + fract(uv.y + iTime * 0.5 - uv.y);

    //Horizontal glitches
    float displ = floor(uv.y * iResolution.y * 2. );
    uv.x += uv.x * mod(displ, sin(iTime*10.) * 10.) * 0.003;
    uv.x -= uv.x * mod(displ, tan(iTime*10.) * 10.) * 0.0005;

    vec4 texColor = texture(iChannel0,uv);
    vec4 noise = texture(iChannel1, xy);
    noise *= 0.2;

    fragColor = texColor+noise;

    //Colour distortion
    float index1 = floor(uv.y * tan(iTime) * 10.) * cos(iTime);
    float index2 = floor(uv.y * 50.) * cos(iTime) * 4.;

    fragColor.r *= mod(index1, 2.) * 0.2;
    fragColor.b += mod(index2, 2.) * 0.2;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}





