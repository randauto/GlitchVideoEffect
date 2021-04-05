#extension GL_OES_standard_derivatives : enable
precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

#define STATIC_ON true
#define STATIC_FADE_PERIOD 0.7

#define BAR_NOISE_ON true

#define STATIC_NOISE_THRESHOLD 0.8
#define BAR_NOISE_THRESHOLD 0.98
#define MAX_BAND_WIDTH 50.

float randomNoise(float inputValue)
{
    return ((sin(inputValue*344.3423+434.21222*inputValue)/8.)+.125)
    + ((cos(inputValue*456.15+5543.53428*inputValue)/8.)+.125)
    + ((sin(inputValue*54.21111+455.24534*inputValue)/8.)+.125)
    + ((cos(inputValue*54.21111+951.23984*inputValue)/8.)+.125);
}


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = fragCoord/iResolution.xy;

    vec3 XYStaticCol = vec3(1.);
    vec3 YBarStaticCol = vec3(0.7);
    vec3 vhsBlueCol = vec3(0.,0.,.8);
    vec3 finalCol = vec3(0.);

    // Useful Signals
    float YBarNoise = randomNoise(fragCoord.y*iGlobalTime);
    float XYStaticNoise = randomNoise(fragCoord.x*fragCoord.y*0.2);
    float sinBackAndForth = (sin(iGlobalTime)/2.)+.5;
    vec4 videoSignal = texture2D(
    sTexture,
    vec2(
    fract(uv.x+(fract(iGlobalTime)/(100.+iGlobalTime))),
    fract(uv.y+(0.01*sin(iGlobalTime/YBarNoise)*fract(iGlobalTime/3.)))
    )
    );


    // Start with Blue
    finalCol = videoSignal.rgb;

    vec3 screenDoor = vec3(
    .2*(5.*sin((fract(iGlobalTime/6.)+10.)*fragCoord.y)+1.),
    .2*(5.*cos((fract(iGlobalTime/6.)+10.)*fragCoord.x)+1.),
    1.);

    finalCol = mix(screenDoor, finalCol, .8);

    // Output Final color to screen
    fragColor = vec4(finalCol,1.0);
}

void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
