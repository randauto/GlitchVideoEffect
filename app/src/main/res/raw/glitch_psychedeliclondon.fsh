
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

    // Time varying pixel color
    vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));


    vec4 noiseR = texture(iChannel1, vec2(uv.x/30., uv.y/30. + iTime/100.));
    vec4 noiseG = texture(iChannel1, vec2(uv.x/20., uv.y/20. + iTime/90.));
    vec4 noiseB = texture(iChannel1, vec2(uv.x/40., uv.y/40. + iTime/80.));

    vec4 imageR = texture(iChannel0, uv + noiseR.xz/10.);
    vec4 imageG = texture(iChannel0, uv + noiseG.xz/20.);
    vec4 imageB = texture(iChannel0, uv + noiseB.xz/15.);

    vec3 color = vec3(imageR.x, imageG.y, imageB.z);// + noise.xyz;

    // Output to screen
    fragColor = vec4(color.xyz,1.0);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

