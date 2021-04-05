
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;
uniform float               intensity;
 float rand(vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
}

 void main() {

     gl_FragColor = texture2D(iChannel0,textureCoordinate);

     float t = mod(iTime,2.0);
     if (t > 1.5) {
         float x = rand(vec2(t));
         float y = rand(vec2(x,t));
         x = mod(x,0.1) - 0.05;
         y = mod(y,0.1) - 0.05;
         gl_FragColor = mix(gl_FragColor,texture2D(iChannel0,textureCoordinate + vec2(x,y)),0.5);
     }

         vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }
