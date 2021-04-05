
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
  precision highp float;

  varying  vec2 textureCoordinate;

  uniform sampler2D iChannel0;

  uniform  float iTime;

  uniform  vec3 iResolution;
uniform float               intensity;
   vec3 palette[4];
  float palgray[4];
   vec3 lum = vec3(0.2126,0.7152,0.0722);

  void main() {
      palette[0] = vec3(15,56,15)/256.0;
      palette[1] = vec3(48,98,48)/256.0;
      palette[2] = vec3(140,173,15)/256.0;
      palette[3] = vec3(156,189,15)/256.0;
      for (int i = 0; i < 4; ++i)
          palgray[i] = dot(palette[i], lum);
      for (int i = 0; i < 4; ++i)
          palgray[i] /= palgray[3];

       vec2 uv = textureCoordinate;
      uv = uv - mod(uv, 0.01);

      float gray = dot(texture(iChannel0, uv.xy).rgb, lum);
       vec3 color;
      for (int i = 0; i < 4; ++i) {
          if (gray <= palgray[i]) {
              color = palette[i];
              break;
          }
      }

      fragColor = vec4(color, 1);

      vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
  }