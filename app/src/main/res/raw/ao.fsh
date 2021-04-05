#extension GL_OES_standard_derivatives : enable
precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

float maxStrength = 0.5;
float minStrength = 0.125;

float speed = 10.00;

float random (vec2 noise)
{
    //--- Noise: Low Static (X axis) ---
    //return fract(sin(dot(noise.yx,vec2(0.000128,0.233)))*804818480.159265359);

    //--- Noise: Low Static (Y axis) ---
    //return fract(sin(dot(noise.xy,vec2(0.000128,0.233)))*804818480.159265359);

    //--- Noise: Low Static Scanlines (X axis) ---
    //return fract(sin(dot(noise.xy,vec2(98.233,0.0001)))*925895933.14159265359);

    //--- Noise: Low Static Scanlines (Y axis) ---
    //return fract(sin(dot(noise.xy,vec2(0.0001,98.233)))*925895933.14159265359);

    //--- Noise: High Static Scanlines (X axis) ---
    return fract(sin(dot(noise.xy,vec2(0.0001,98.233)))*12073103.285);

    //--- Noise: High Static Scanlines (Y axis) ---
//    return fract(sin(dot(noise.xy,vec2(98.233,0.0001)))*12073103.285);

    //--- Noise: Full Static ---
//    return fract(sin(dot(noise.xy,vec2(10.998,98.233)))*12433.14159265359);
}


float random_colour (float noise)
{
    return fract(sin(noise));
}


void mainImage(out vec4 fragColour, in vec2 fragCoord)
{

    vec2 uv = fragCoord.xy / iResolution.xy;
    vec2 uv2 = fract(fragCoord.xy/iResolution.xy*fract(sin(iGlobalTime*speed)));

    //--- Strength animate ---
    maxStrength = clamp(sin(iGlobalTime/2.0),minStrength,maxStrength);
    //-----------------------

    //--- Black and white ---
    vec3 colour = vec3(random(uv2.xy))*maxStrength;
    //-----------------------


    colour.r *= random_colour(sin(iGlobalTime*speed));
    colour.g *= random_colour(cos(iGlobalTime*speed));
    colour.b *= random_colour(tan(iGlobalTime*speed));


    //--- Background ---
    vec3 background = vec3(texture2D(sTexture, uv));
    //--------------

    fragColour = vec4(background-colour,1.0);
}

void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
