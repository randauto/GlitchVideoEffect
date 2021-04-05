#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;

float nrand(in float x, in float y)
{
    return fract(sin(dot(vec2(x, y), vec2(12.9898, 78.233))) * 43758.5453);
}

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    float u = textureCoordinate.x;
    float v = textureCoordinate.y;
    float jitter = nrand(v, 0.0) * 2.0 - 1.0;
    float drift = iGlobalTime;
    vec2 uScanLineJitter = iResolution.xy;
    float offsetParam = step(uScanLineJitter.y, abs(jitter));
    jitter = jitter * offsetParam * uScanLineJitter.x;
    vec4 color1 = texture2D(sTexture, fract(vec2(u + jitter, v)));
    vec4 color2 = texture2D(sTexture, fract(vec2(u + jitter + v*drift, v)));
    fragColor = vec4(color1.r, color2.g, color1.b, 1.0);
}

void main() {
    mainImage(gl_FragColor, textureCoordinate.xy);
}



