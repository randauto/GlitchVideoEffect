precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float               intensity;
 void main() {
      vec2 uv = textureCoordinate;

      float t = mod(iTime/0.5,1.0);

     if (t < 0.2) {
         t = t / 0.2;
         uv.x += (0.5-uv.x)*(t*0.1);
         uv.y += (0.5-uv.y)*(t*0.1);
     } else if (t < 0.6) {
         t = (t - 0.2) / 0.4;
         uv.x += (0.5-uv.x)*(0.1 + t*-0.075);
         uv.y += (0.5-uv.y)*(0.1 + t*-0.075);
     } else {
         t = (t - 0.6) / 0.4;
         uv.x += (0.5-uv.x)*(0.025 + t*-0.025);
         uv.y += (0.5-uv.y)*(0.025 + t*-0.025);
     }

     vec4 newcolor = texture2D(iChannel0,uv);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + newcolor.rgb*intensity,1.);
 }
 