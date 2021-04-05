
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

  uniform vec3 iResolution;
  uniform float               intensity;
  uniform vec2 iMouse;
 float c_textureSize = 512.0;

 void main() {

     vec2 uv = textureCoordinate;

     vec3 tex = texture(iChannel0, uv).xyz;

     float bounds = .5;
     if(iMouse.x > 0. && iMouse.y > 0.) bounds = iMouse.x / 1.0;

     fragColor = vec4(uv.x < bounds ? clamp(dot(tex, tex), 0., 1.) : (tex.x + tex.y + tex.z) / 3.);

//     float lineWidth = .005;
//     if(uv.x > bounds - lineWidth && uv.x < bounds + lineWidth) {
//         fragColor = vec4(1.) - fragColor;
//     }
vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
