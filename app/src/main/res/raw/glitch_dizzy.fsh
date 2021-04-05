precision highp float;
varying vec2 textureCoordinate;
 uniform sampler2D iChannel0;
 uniform float iTime;
 uniform float  intensity;
 void main() {
      vec2 uv = textureCoordinate;
      float d = (1.0 - abs(mod((iTime + 5.0),2.0) - 1.0)) * 0.1;
      vec4 c0 = texture2D(iChannel0,uv);
      vec4 c1 = texture2D(iChannel0,uv + vec2(d,0.0));
      vec4 c2 = texture2D(iChannel0,uv - vec2(d,0.0));
     vec4 newcolor = mix(mix(c0,c1,0.5),c2,1.0/3.0);

    gl_FragColor = vec4(c0.rgb*(1.-intensity) + newcolor.rgb*intensity,1.);
 }