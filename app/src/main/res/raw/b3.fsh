#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;

void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    //Sawtooth function to pulse from centre.
    float offset = (iGlobalTime- floor(iGlobalTime))/iGlobalTime;
    float CurrentTime = (iGlobalTime)*(offset);

    vec3 WaveParams = vec3(0.1, 0.1, 0.1);

    float ratio = iResolution.y/iResolution.x;

    //Use this if you want to place the centre with the mouse instead
    //vec2 WaveCentre = vec2( iMouse.xy / iResolution.xy );

    vec2 WaveCentre = vec2(0.5, 0.5);
    WaveCentre.y *= ratio;

    vec2 texCoord = fragCoord.xy / iResolution.xy;
    texCoord.y *= ratio;
    float Dist = distance(texCoord, WaveCentre);


    vec4 Color = texture2D(sTexture, texCoord);

    //Only distort the pixels within the parameter distance from the centre
    if ((Dist <= ((CurrentTime) + (WaveParams.z))) &&
    (Dist >= ((CurrentTime) - (WaveParams.z))))
    {
        //The pixel offset distance based on the input parameters
        float Diff = (Dist - CurrentTime);
        float ScaleDiff = (pow(abs(Diff * WaveParams.x), WaveParams.y));
        float DiffTime = (Diff  * ScaleDiff);

        //The direction of the distortion
        vec2 DiffTexCoord = normalize(texCoord - WaveCentre);

        //Perform the distortion and reduce the effect over time
        texCoord += ((DiffTexCoord * DiffTime) / (CurrentTime * Dist * 40.0));
        Color = texture2D(sTexture, texCoord);

        //Blow out the color and reduce the effect over time
        Color += (Color * ScaleDiff) / (CurrentTime * Dist * 40.0);
    }

    fragColor = Color;
}

void main() {
    mainImage(gl_FragColor, textureCoordinate.xy*iResolution.xy);
}



