precision highp float;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

float remap(float value, float inputMin, float inputMax, float outputMin, float outputMax)
{
    return (value - inputMin) * ((outputMax - outputMin) / (inputMax - inputMin)) + outputMin;
}

void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 uv = fragCoord.xy;
    float normalizedContrast = 1.0;
    float contrast = remap(normalizedContrast, 0.0, 1.0, 0.2 /*min*/, 4.0 /*max*/);

    vec4 srcColor = texture2D(sTexture, uv);
    vec4 dstColor = vec4((srcColor.rgb - vec3(0.5)) * contrast + vec3(0.5), 1.0);
    fragColor = clamp(dstColor, 0.0, 1.0);
}

void main() {
    mainImage(gl_FragColor, textureCoordinate);
}