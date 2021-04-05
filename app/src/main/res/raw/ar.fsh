#extension GL_OES_standard_derivatives : enable
precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

const int maxDroplets = 10;


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // distance from center of image, used to adjust blur
    vec2 uv = fragCoord.xy / iResolution.xy;
    float d = length(uv - vec2(0.5,0.5));

    // blur amount
    float blur = 0.02;
    //blur = (1.0 + sin(iTime*6.0)) * 0.5;
    //blur *= 1.0 + sin(iTime*16.0) * 0.5;
    //blur = pow(blur, 3.0);
    //blur *= 0.05;
    // reduce blur towards center
    //blur *= d;

    float myTime = iGlobalTime * 1.0;

    // fragColor = texture( iChannel0, vec2(uv.x + sin( (uv.y + sin(myTime)) * abs(sin(myTime) + sin(2.0 * myTime) + sin(0.3 * myTime) + sin(1.4 * myTime) + cos(0.7 * myTime) + cos(1.3 * myTime)) * 4.0 ) * 0.02,uv.y) );

    vec2 myuv =  vec2(uv.x + sin( (uv.y + sin(myTime)) * abs(sin(myTime) + sin(2.0 * myTime) + sin(0.3 * myTime) + sin(1.4 * myTime) + cos(0.7 * myTime) + cos(1.3 * myTime)) * 4.0 ) * 0.02,uv.y) ;

    // final color
    vec3 col;
    col.r = texture2D( sTexture, vec2(myuv.x+blur,myuv.y) ).r;
    col.g = texture2D( sTexture, myuv ).g;
    col.b = texture2D( sTexture, vec2(myuv.x-blur,myuv.y) ).b;

    // scanline
    float scanline = sin(uv.y*400.0)*0.08;
    col -= scanline;

    // vignette
    col *= 1.0 - d * 0.5;

    fragColor = vec4(col,1.0);
}
void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
