#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;

float rand(vec2 co){
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453);
}

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    // Sawtooth calc of time
    float offset = (iGlobalTime - floor(iGlobalTime)) / iGlobalTime;
    float time = iGlobalTime * offset;

    // Wave design params
    vec3 waveParams = vec3(10.0, 0.8, 0.1);


    // Find coordinate, flexible to different resolutions
    float maxSize = max(iResolution.x, iResolution.y);
    vec2 uv = fragCoord.xy / maxSize;


    // Find center, flexible to different resolutions
    vec2 center = iResolution.xy / maxSize / 2.;

    // Distance to the center
    float dist = distance(uv, center);

    time = pow(time, 0.55);

    // Original color
    vec4 c = texture2D(sTexture, uv);

    if (time > 0. && dist <= time + waveParams.z && dist >= time - waveParams.z) {

        // The pixel offset distance based on the input parameters
        float diff = (dist - time);
        float diffPow = (1.0 - pow(abs(diff * waveParams.x), waveParams.y));
        float diffTime = (diff  * diffPow);

        // The direction of the distortion
        vec2 dir = normalize(uv - center);


        uv.x += (rand(vec2(iGlobalTime, fragCoord.y))-1.0) / ((time * dist * 3000.0));

        // Perform the distortion and reduce the effect over time
        uv += ((dir * diffTime) / (time * dist * 20.0)) * (dist / 2.0);


        c = texture2D(sTexture, uv);

        vec4 red = texture2D(sTexture, vec2(uv.x - (0.2 / (time* dist * 500.0)), uv.y)) * vec4(1.0, 0.0, 0.0, 1.0);
        vec4 green = texture2D(sTexture, vec2(uv.x + (0.2 / (time* dist * 500.0)), uv.y))* vec4(0.0, 1.00, 0.0, 1.0);
        vec4 blue  = texture2D(sTexture, vec2(uv.x, uv.y)) * vec4(0.0, 0.0, 1.0, 1.0);
        c += red + green + blue;
        c /= 2.;
    }

    if (c.r > 0.2) c.a = 0.0;

    fragColor = c;
}

void main() {
    mainImage(gl_FragColor, textureCoordinate.xy*iResolution.xy);
}



