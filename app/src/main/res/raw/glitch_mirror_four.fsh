
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

  uniform  vec3 iResolution;
uniform float               intensity;
 void main() {
      vec2 uv = textureCoordinate;

     uv = (0.5 - abs(uv - 0.5)) * 2.0;

     fragColor = texture(iChannel0,uv);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
