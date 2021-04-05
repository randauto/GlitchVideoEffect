
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float               intensity;
#define SIN01(a) (sin(a)*0.5 + 0.5)

  vec3 rgb2hsv( vec3 c)
{
     vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
     vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
     vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

     float d = q.x - min(q.w, q.y);
     float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

 void main() {

      vec2 uv = textureCoordinate;

      vec3 hsv = rgb2hsv(texture(iChannel0, uv).rgb);

      float angle = hsv.x + atan(uv.y, uv.x) + iTime*0.1;
      mat2 RotationMatrix = mat2( cos( angle ), -sin( angle ), sin( angle ),  cos( angle ));
      vec3 col = texture(iChannel0, uv + RotationMatrix * vec2(log(max(SIN01(iTime*0.7)-0.2, 0.)*0.2 + 1.  ),0) * hsv.y).rgb;

     gl_FragColor = vec4(col,1.0);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);

 }

