
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;
uniform float               intensity;
 void main() {

     float POWER = 0.04; // How much the effect can spread horizontally
     float VERTICAL_SPREAD = 7.0; // How vertically is the sin wave spread
     float ANIM_SPEED = 0.4; // Animation speed

     vec2 uv = textureCoordinate;
     float y = (uv.y + iTime * ANIM_SPEED) * VERTICAL_SPREAD;

     uv.x += (
              // This is the heart of the effect, feel free to modify
              // The sin functions here or add more to make it more complex
              // and less regular
              sin(y)
              + sin(y * 10.0) * 0.2
              + sin(y * 50.0) * 0.03
              )
     * POWER // Limit by maximum spread
     * sin(uv.y * 3.14) // Disable on edges / make the spread a bell curve
     * sin(iTime); // And make the power change in time

     gl_FragColor = texture(iChannel0, uv);

           vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }

