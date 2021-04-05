
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

 uniform vec3 iResolution;
uniform float               intensity;
void main()
{
	vec2 R=iResolution.xy;
	vec2 uv = iResolution.xy * textureCoordinate;
    uv = (2.*uv - R) / R.y;

    float r = length(uv);

    uv.x = r*r/uv.x;
    uv.y = uv.y/r/r;

    uv.x -= iTime;

    uv = mod(uv+.5,2.); uv -=2.*clamp(uv-1.,0.,1.); // tile and flip

	fragColor = texture(iChannel0, uv);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


