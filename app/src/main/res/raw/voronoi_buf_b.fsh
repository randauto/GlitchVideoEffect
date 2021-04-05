precision highp float;

uniform int                 iFrame;
uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
uniform sampler2D           iChannel1;
varying vec2                texCoord;
uniform float               intensity;

// how many JFA steps to do.  2^c_maxSteps is max image size on x and y
const float c_maxSteps = 10.0;

//============================================================
vec4 StepJFA (in vec2 fragCoord, in float level)
{
    float stepwidth = floor(exp2(c_maxSteps - 1. - level)+0.5);

    float bestDistance = 9999.0;
    vec2 bestCoord = vec2(0.0);

    for (int y = -1; y <= 1; ++y) {
        for (int x = -1; x <= 1; ++x) {
            vec2 sampleCoord = fragCoord + vec2(x,y) * stepwidth;

            vec4 data = texture2D( iChannel0, sampleCoord / iResolution.xy);
            vec2 seedCoord = data.xy * iResolution.xy;
            float dist = length(seedCoord - fragCoord);
            if ((seedCoord.x != 0.0 || seedCoord.y != 0.0) && dist < bestDistance)
            {
                bestDistance = dist;
                bestCoord = seedCoord;
            }
        }
    }

    return vec4(bestCoord / iResolution.xy, 0.0, 0.0);
}

//============================================================
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    float fFrame = float(iFrame);
    float level = mod(fFrame,c_maxSteps);
    if (level < .5) {
        if (texture2D(iChannel1, fragCoord / iResolution.xy).w > .5)
        	fragColor = vec4(fragCoord / iResolution.xy, 0.0, 0.0);
        else
            fragColor = vec4(0.0);
        return;
    }

    fragColor = StepJFA(fragCoord, level);

    vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
	mainImage(gl_FragColor, texCoord*iResolution.xy);
}