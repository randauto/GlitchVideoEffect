
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying vec2 textureCoordinate;
 varying vec2 textureCoordinate2;

 uniform sampler2D iChannel0;
 uniform sampler2D iChannel1;
uniform float               intensity;
 uniform float iTime;

 void main() {
     vec2 uv = textureCoordinate;
     vec2 block = floor(textureCoordinate.xy * vec2(750.0,422.0) / vec2(24));
     vec2 uv_noise = block / vec2(64);
     uv_noise += floor(vec2(iTime) * vec2(1234.0, 3543.0)) / vec2(64);

     float block_thresh = pow(fract(iTime * 1236.0453), 2.0) * 0.05;
     float line_thresh = pow(fract(iTime * 2236.0453), 3.0) * 0.10;

     vec2 uv_r = uv;
     vec2 uv_g = uv;
     vec2 uv_b = uv;

     // glitch some blocks and lines
     if (texture2D(iChannel1, fract(uv_noise)).r < block_thresh ||
         texture2D(iChannel1, fract(vec2(uv_noise.y, 0.0))).g < line_thresh) {

         vec2 dist = (fract(uv_noise) - 0.5) * 0.3;
         uv_r += dist * 0.1;
         uv_g += dist * 0.2;
         uv_b += dist * 0.125;
     }

     fragColor.r = texture2D(iChannel0, fract(uv_r)).r;
     fragColor.g = texture2D(iChannel0, fract(uv_g)).g;
     fragColor.b = texture2D(iChannel0, fract(uv_b)).b;

     // loose luma for some blocks
     if (texture2D(iChannel1, fract(uv_noise)).g < block_thresh)
         fragColor.rgb = fragColor.ggg;

     // discolor block lines
     if (texture2D(iChannel1, fract(vec2(uv_noise.y, 0.0))).b * 3.5 < line_thresh)
         fragColor.rgb = vec3(0.0, dot(fragColor.rgb, vec3(1.0)), 0.0);

     // interleave lines in some blocks
     if (texture2D(iChannel1, fract(uv_noise)).g * 1.5 < block_thresh ||
         texture2D(iChannel1, fract(vec2(uv_noise.y, 0.0))).g * 2.5 < line_thresh) {
         float line = fract(textureCoordinate.y * 422.0 / 3.0);//todo
         vec3 mask = vec3(3.0, 0.0, 0.0);
         if (line > 0.333)
             mask = vec3(0.0, 3.0, 0.0);
         if (line > 0.666)
             mask = vec3(0.0, 0.0, 3.0);

         fragColor.xyz *= mask;

               vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
     }
 }
