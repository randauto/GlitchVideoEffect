
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
     if (uv.x < 0.5) {
         uv.x += 0.25;
     } else {
         uv.x -= 0.25;
         uv.x = 1.0 - uv.x;
     }
     fragColor = texture(iChannel0,uv);
    fragColor *= (1.5-intensity);
    if(intensity == 0.0){
        vec2 uvori = textureCoordinate.xy;
        vec4 texori = texture2D(iChannel0, uvori);
        gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
    }
 }

