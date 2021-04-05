
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
vec4 scan(vec2 uv, float _ScanSize, float _ScanBend){

    float bulge = 1.0 +  length( uv - vec2(.5,.5) ) * _ScanBend;

    float lum = abs(sin( bulge * (uv.y + uv.x*.1)*_ScanSize + iTime))+.1;
    //lum -= (cos((uv.y* .2 + uv.x + (5.0 * sin(iTime * 3.0))) * 2.0 ) * sin(uv.y * 49.0)) * .2;
    return vec4(lum,lum,lum,lum);
}

void main()
{
	vec2 uv = textureCoordinate;
	fragColor = scan( uv + vec2(0, cos(.4*iTime)), 10.0  + 5.0 * sin(iTime), .50) *texture(iChannel0, uv);

      vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

