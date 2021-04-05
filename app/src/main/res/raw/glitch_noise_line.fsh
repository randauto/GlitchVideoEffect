
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 //random hash
  vec4 hash42( vec2 p) {
      vec4 p4 = fract(vec4(p.xyxy) * vec4(443.8975,397.2973, 491.1871, 470.7827));
     p4 += dot(p4.wzxy, p4+19.19);
     return fract(vec4(p4.x * p4.y, p4.x*p4.z, p4.y*p4.w, p4.x*p4.w));
 }

  float hash( float n) {
     return fract(sin(n)*43758.5453123);
 }

 // 3d noise function (iq's)
  float n( vec3 x){
      vec3 p = floor(x);
      vec3 f = fract(x);
     f = f*f*(3.0-2.0*f);
      float n = p.x + p.y*57.0 + 113.0*p.z;
      float res = mix(mix(mix( hash(n+  0.0), hash(n+  1.0),f.x),
                               mix( hash(n+ 57.0), hash(n+ 58.0),f.x),f.y),
                           mix(mix( hash(n+113.0), hash(n+114.0),f.x),
                               mix( hash(n+170.0), hash(n+171.0),f.x),f.y),f.z);
     return res;
 }

 //tape noise
  float nn( vec2 p){

      float y = -p.y;
      float s = iTime*2.;

      float v = (n(vec3(y*.01 +s, 1., 1.0)) + .0)
     *(n(vec3(y*.011+1000.0+s, 1., 1.0)) + .0)
     *(n(vec3(y*.51+421.0+s, 1., 1.0)) + .0);
     //v*= n( vec3( (fragCoord.xy + vec2(s,0.))*100.,1.0) );
     v*= hash42(vec2(p.x +iTime*0.01, p.y)).x +.3;

     v = pow(v+.3, 1.);
     if(v<.7) v = 0.;  //threshold
     return v;
 }

 void main() {

      vec2 uv = textureCoordinate;

     uv = floor(uv*vec2(750.0,422.0));

      float col = nn(uv);

     fragColor = texture2D(iChannel0, textureCoordinate);

     fragColor = fragColor + vec4(vec3(col),1.0);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
 