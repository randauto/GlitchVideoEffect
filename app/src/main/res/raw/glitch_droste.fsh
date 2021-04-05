
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 void main() {
     // Customizable Parameters
     float repeat = 10.0* intensity;            // How many times it should be repeated.

     // UV in 0..1 space and also inverted UV (1..0).
      vec2 uv = textureCoordinate;
      vec2 uv_inverted = 1.0 - uv;

     // Calculate the offset_multiplier using the current uv.
     // Since droste works by only repeating when both uv parts are above a threshold,
     // we can just use the min() operator twice on it to get the final multiplier.
      vec2 fiter = floor(uv * repeat * 2.0);            // Calculate forward UV multiplier.
      vec2 riter = floor(uv_inverted * repeat * 2.0);    // Calculate reverse UV multiplier.
      vec2 iter = min(fiter, riter);                    // iter = min(forward UV, reverse UV)
     float offset_mul = min(iter.x, iter.y);            // min(iter.x, iter.y) - Our Droste multiplier.

     // Offset is calculated by taking half the view and dividing it by the amount of repeats,
     //  and then we multiply it by the multiplier above.
      vec2 offset = (vec2(0.5, 0.5) / repeat) * offset_mul;

     // The final step is to fix the UVs that are inside a Droste offset back to 0..1 range.
     // You can do this by calculating the new drawable area and divide 1.0 by this area.
      vec2 uv_mul = 1.0 / (vec2(1.0, 1.0) - offset * 2.0);

     // Calculate the final UV: Screen UV minus Offset, clamped to 0..1, times UV Multiplier.
     // The clamp could technically be skipped if out-of-bounds UVs are not a problem.
     //vec2 finaluv = clamp(uv - offset, 0.0, 1.0) * uv_mul;
      vec2 finaluv = (uv - offset) * uv_mul;

     // And now just read the texture.
     fragColor = texture(iChannel0, finaluv);

    //  vec2 uvori = textureCoordinate.xy;
    // vec4 texori = texture2D(iChannel0, uvori);
    // gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
