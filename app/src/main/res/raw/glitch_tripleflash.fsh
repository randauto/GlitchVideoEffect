
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

 uniform vec3 iResolution;
uniform float               intensity;
 float gamma = 2.2;  // different on windows, mac, linux, or old monitor, or tuned on monitor or preferences)

#define togamma(x) pow(x,gamma);
#define ungamma(x) pow(x,1./gamma);


 void main() {
     vec2 uv = textureCoordinate;
     uv.y = 1.0 - uv.y;
     float t = iTime;
     vec2 mouse = vec2(0.0);
     vec4 txt = texture(iChannel0,vec2(uv.x,1.-uv.y));
     //if (iMouse.z>0.) gamma = 4.*mouse.y;

     //     if (uv.y<.05)                 // bottom: gamma(50%)
     //         fragColor = vec4(pow(.5,1./gamma));
     //     else if ((uv.y>.9)) // top: space dithering of txt and reversed (thanks Trisomie21).
     //         fragColor = step(fract(fragCoord.x*fragCoord.y), .5)>0. ? txt : pow(1.-pow(txt,vec4(gamma)),vec4(1./gamma));
     //     else
     if (uv.x<.33) {
         t = mod(4.*t,2.);        // left: for reference, slow time-alterning
         fragColor = (t>1.) ? txt : 1.-txt;
     }
     else {
         t =  mod(59.6*t,2.);    // programm is 60fps on my computure

         if (uv.x<.66)            // middle: time-dithering without gamma
             fragColor = (t>1.) ? txt : 1.-txt;
         else                        // right: time-dithering without gamma
             fragColor = (t>1.) ? txt : pow(1.-pow(txt,vec4(gamma)),vec4(1./gamma));
     }

        vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
