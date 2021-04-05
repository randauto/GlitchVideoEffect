
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float               intensity;
 void main() {
      vec2 uv = textureCoordinate;

     if (uv.x <= 0.25) {
         uv.x = (0.25 - uv.x) * 2.0;
     } else if (uv.x <= 0.75) {
         uv.x = (uv.x - 0.25) * 2.0;
     } else {
         uv.x = (0.5 - (uv.x - 0.75)) * 2.0;
     }

     if (uv.y <= 0.25) {
         uv.y = (0.25 - uv.y) * 2.0;
     } else if (uv.y <= 0.75) {
         uv.y = (uv.y - 0.25) * 2.0;
     } else {
         uv.y = (0.5 - (uv.y - 0.75)) * 2.0;
     }

     fragColor = texture(iChannel0,uv);
     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }

