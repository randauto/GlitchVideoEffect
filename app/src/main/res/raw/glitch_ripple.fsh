
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 void main() {

     // vec2 uv = textureCoordinate;

     // pixel position normalised to [-1, 1]
      vec2 cPos = -1.0 + 2.0 * textureCoordinate;

     // distance of current pixel from center
     float cLength = length(cPos);

      vec2 uv = textureCoordinate+(cPos/cLength)*cos(cLength*12.0-iTime*4.0) * 0.03;


      vec3 col = texture(iChannel0,uv).xyz;

     fragColor = vec4(col,1.0);

      vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);

 }
