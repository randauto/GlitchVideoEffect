
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

     float amount = 0.0;

     amount = (1.0 + sin(iTime*6.0)) * 0.5;
     amount *= 1.0 + sin(iTime*16.0) * 0.5;
     amount *= 1.0 + sin(iTime*19.0) * 0.5;
     amount *= 1.0 + sin(iTime*27.0) * 0.5;
     amount = pow(amount, 3.0);

     amount *= 0.05;

      vec3 col;
     col.r = texture( iChannel0, vec2(uv.x+amount,uv.y) ).r;
     col.g = texture( iChannel0, uv ).g;
     col.b = texture( iChannel0, vec2(uv.x-amount,uv.y) ).b;

     col *= (1.0 - amount * 0.5);

     fragColor = vec4(col,1.0);

       vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
