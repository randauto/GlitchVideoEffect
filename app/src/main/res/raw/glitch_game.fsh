
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
#define darkIsHot 1

 void main() {
      vec2 uv = textureCoordinate;
      vec3 texColor = texture(iChannel0,uv).rgb;


     float a = texColor.r;
#if darkIsHot
     a = 1.0 - a;
#endif

     //fast shader version
     fragColor.r = 1.0 - clamp(step(0.166, a)*a, 0.0, 0.333) - 0.667*step(0.333, a) + step(0.666, a)*a + step(0.833, a)*1.0;
     fragColor.b = clamp(step(0.333, a)*a, 0.0, 0.5) + step(0.5, a)*0.5;
     fragColor.g = clamp(a, 0.0, 0.166) + 0.834*step(0.166, a) - step(0.5, a)*a - step(0.666, a)*1.0;

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
