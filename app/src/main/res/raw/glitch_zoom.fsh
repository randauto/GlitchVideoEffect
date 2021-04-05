
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

  uniform vec3 iResolution;
uniform float               intensity;

 float c_depthX = 9.0;  // in pixels, how far on the x axis the flange moves
 float c_depthY = 2.0;  // in pixels, how far on the y axis the flange moves
 float c_frequencyX = 4.3; // in Hz, the frequency of the sine wave controling the x axis flange
 float c_frequencyY = 7.14; // in Hz, the frequency of the sine wave controling the x axis flange
 float c_textureSize = 512.0;

 void main() {

     float c_pixelSize = (1.0 / c_textureSize);
     // interactive mode settings
     //     if (iMouse.z > 0.0)
     //     {
     //         c_frequencyX = 100.0 * iMouse.x / iResolution.x;
     //         c_depthX = 25.0 * iMouse.y / iResolution.y;
     //         c_frequencyY = 0.0;
     //         c_depthY = 0.0;
     //     }

     // calculate the uv and offset for our uv
     vec2 uv = textureCoordinate;

     vec2 offset = vec2(
                              (sin(iTime*c_frequencyX) * 0.5 + 0.5) * c_pixelSize*c_depthX,
                              (sin(iTime*c_frequencyY) * 0.5 + 0.5) * c_pixelSize*c_depthY
                              );

     // get our value and our offset value

     vec3 a = texture(iChannel0, uv).rgb;
     vec3 b = vec3(
                         texture(iChannel0, uv + offset.xy).r,
                         texture(iChannel0, uv + offset.yx).g,
                         texture(iChannel0, uv + vec2(offset.y, -offset.x)).b
                         );

     //     vec3 a = texture(iChannel0, uv).rgb;
     //     vec3 b = texture(iChannel0, uv + offset).rgb;

     // convert from 0-1 space to -1 to 1 space so we can have cancelation when doing addition
     a = a * 2.0 - 1.0;
     b = b * 2.0 - 1.0;

     // add (mix)
     vec3 color = a + b;

     // convert from -1 to 1 space to 0 to 1 space again
     color = color * 0.5 + 0.5;

     // set the color
     fragColor = vec4(clamp(color, 0.0, 1.0), 1.0);

            vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }
