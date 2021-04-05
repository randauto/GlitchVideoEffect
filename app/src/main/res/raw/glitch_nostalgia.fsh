
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float  intensity;
  float rand( vec2 co) {
     return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
 }

 const  vec3 W = vec3(0.3333);

 void main()
 {
     lowp vec4 textureColor = texture2D(iChannel0, textureCoordinate);
     gl_FragColor = textureColor;

     float time = mod(iTime,4.5);
     if (time >= 0.0) {
         if (time < 2.5) {
             float t = mod(time,2.0);
             float x = rand(vec2(t));
             float y = rand(vec2(x,t));
             x = mod(x,0.1) - 0.05;
             y = mod(y,0.1) - 0.05;
             gl_FragColor = mix(gl_FragColor,texture2D(iChannel0,textureCoordinate + vec2(x,y)),0.5);
         } else {
             float t2 = (time - 2.5) * 0.5;
             if (textureCoordinate.x > t2) {
                 float luminance = dot(textureColor.rgb, W);
                 gl_FragColor = vec4(vec3(luminance), textureColor.a);
             }
         }
     }
     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }

