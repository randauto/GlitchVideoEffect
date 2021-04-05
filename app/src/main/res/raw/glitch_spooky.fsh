
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
 uniform  float iDist;
 uniform  float iSet;
 uniform  float iAlpha;
uniform float               intensity;
 void main() {

      vec2 uv = textureCoordinate;

     uv.x += iTime;
     gl_FragColor = texture2D(iChannel0,uv);
     uv.x += iDist;
     gl_FragColor = mix(gl_FragColor,texture2D(iChannel0,uv),rand(2));

       vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }
