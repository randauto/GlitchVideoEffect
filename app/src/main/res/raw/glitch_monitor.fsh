
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 float rand( vec2 co) {
     return fract(sin(mod(dot(co.xy ,vec2(12.9898,78.233)),3.14))*43758.5453);
 }

 void main() {
      vec2 uv = textureCoordinate;

     float lum = cos(uv.y * iResolution.y);
     lum*=lum;
     lum/=3.;
     lum+=0.6+rand(uv*iTime)/6.;

     float col = dot(texture(iChannel0,uv).rgb,vec3(0.65,0.3,0.1)*lum);

     fragColor = vec4(0,col,0,1.)*smoothstep(0.9,0.,distance(uv,vec2(0.5)));

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
