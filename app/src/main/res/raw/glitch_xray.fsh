
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

  uniform vec3 iResolution;
uniform float               intensity;
 void main() {

     gl_FragColor = texture2D(iChannel0,textureCoordinate);

     if (mod(iTime,0.25) < 0.125) {
         gl_FragColor = vec4((1.0 - gl_FragColor.rgb), gl_FragColor.w);
     }
            vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }
