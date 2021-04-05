
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
    const float bitDepth = 1.333;//4 bit color (this is bits per-color)
    const float numColors = pow(2.0, bitDepth);
    const mat4 bayer = mat4(
    	0., 8., 2., 10.,
        12., 4., 14., 6.,
        3., 11., 1., 9.,
        15., 7., 13., 5.
    );

    vec2 fragCoord = iResolution.xy * textureCoordinate;
    vec2 uv = fragCoord/iResolution.xy;

    float bayerVal = bayer[int(mod(fragCoord.x, 4.))][int(mod(fragCoord.y, 4.))];

    vec3 col = texture(iChannel0, uv).xyz;

    if(uv.x > 0.333 && uv.x < 2.*0.333){
    	col = col + bayerVal * (1. / (16. * numColors));
    	col = floor(col * numColors) / numColors;
    }else if(uv.x < 0.333){
    	col = floor(col * numColors) / numColors;
    }

    fragColor = vec4(col, 1.);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


