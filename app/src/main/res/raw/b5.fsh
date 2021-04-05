precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

const float range = 0.05;
const float noiseQuality = 25.0;
const float noiseIntensity = 0.0088;
const float offsetIntensity = 0.02;
const float colorOffsetIntensity = 1.3;

float rand(vec2 co)
{
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453);
}

float verticalBar(float pos, float uvY, float offset)
{
    float edge0 = (pos - range);
    float edge1 = (pos + range);

    float x = smoothstep(edge0, pos, uvY) * offset;
    x -= smoothstep(pos, edge1, uvY) * offset;
    return x;
}

void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 uv = fragCoord.xy / iResolution.xy;

    for (float i = 0.0; i < 0.71; i += 0.1313)
    {
        float d = mod(iGlobalTime * i, 1.7);
        float o = sin(1.0 - tan(iGlobalTime * 0.24 * i));
        o *= offsetIntensity;
        uv.x += verticalBar(d, uv.y, o);
    }

    float uvY = uv.y;
    uvY *= noiseQuality;
    uvY = float(int(uvY)) * (1.0 / noiseQuality);
    float noise = rand(vec2(iGlobalTime * 0.00001, uvY));
    uv.x += noise * noiseIntensity;

    vec2 offsetR = vec2(0.006 * sin(iGlobalTime), 0.0) * colorOffsetIntensity;
    vec2 offsetG = vec2(0.0073 * (cos(iGlobalTime * 0.97)), 0.0) * colorOffsetIntensity;

    float r = texture2D(sTexture, uv + offsetR).r;
    float g = texture2D(sTexture, uv + offsetG).g;
    float b = texture2D(sTexture, uv).b;

    fragColor = vec4(r, g, b, 1.0);
}
void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
