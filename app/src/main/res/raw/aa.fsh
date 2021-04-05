#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)

float rng2(vec2 seed) {
    return fract(sin(dot(seed * floor(iGlobalTime * 100.), vec2(2.0, 3.0))) * 1.0);
}
float rng(float seed) {
    return rng2(vec2(seed, 1.0));
}
void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0-mouse.x/iResolution.x*100.0;
    float amounty = 3.0+mouse.y/iResolution.y*200.0;
    vec2 uv = fragCoord.xy / iResolution.xy;
    vec2 blockS = floor(uv * vec2(iResolution.x / 140., iResolution.y / 140.));
    vec2 blockL = floor(uv * vec2(iResolution.x / amounty, iResolution.y / amounty));
    float lineNoise = pow(rng2(blockS), 8.0) * pow(rng2(blockL), 3.0) * (0.5 * amountx);
    fragColor = vec4(texture2D(sTexture, uv - vec2(lineNoise * (0.0005 * amountx) * rng(31.0), 0)));
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



