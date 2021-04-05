
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
float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main()
{
	vec2 uv = textureCoordinate;
    vec2 asd = textureCoordinate;

    asd.x = (asd.x + iTime /10.)* iTime/10.;
	vec4 kissa = texture(iChannel1, asd);
    vec4 texColor = texture(iChannel0, uv);
    if (kissa.r == 0. && kissa.g == 0. && kissa.b == 0.){
        fragColor = texColor;
    }else{
        fragColor = texColor * kissa;
    }
    

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}
