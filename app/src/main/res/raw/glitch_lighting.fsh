
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float               intensity;
 const  vec3 W = vec3(0.3333);

 void main()
 {
     lowp vec4 textureColor = texture2D(iChannel0, textureCoordinate);


     float luminance = dot(textureColor.rgb, W);
//     if (mod(iTime,0.2) > 0.1) {
//         luminance -= 0.5;
//     } else {
//         luminance += 0.3;
//     }

     float t = mod(iTime * 0.75,1.0);
     if (t < 0.33) {
         luminance -= 0.5;
     } else if (t < 0.66) {
          float i = mod(t,0.11) / 0.11 * 7.0;
         //i -= fract(i);
         i = mod(i,2.0);
         if (i < 1.8) {
             luminance += 0.3 - i * 0.1;
         } else {
             luminance -= 0.5;
         }
     } else {
          float i = mod(t,0.11) / 0.11 * 7.0;
         //i -= fract(i);
         i = mod(i,2.0);
         luminance += 0.3 - i * 0.1;
     }

     gl_FragColor = vec4(vec3(luminance), textureColor.a);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
