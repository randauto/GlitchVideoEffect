#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;

const float touchX=350.;
const float touchY=550.;

#define iMouse vec2(touchX, touchY)

float random2d(vec2 n) {
    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 0.7);
}

float randomRange (in vec2 seed, in float min, in float max) {
    return min + random2d(seed) * (max - min);
}
float insideRange(float v, float bottom, float top) {
    return step(bottom, v) - step(top, v);
}

float SPEED = 0.2;

void mainImage(out vec4 fragColor, in vec2 fragCoord){
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0-mouse.x/iResolution.x*1.0;
    float amounty = 0.0+mouse.y/iResolution.y*1.0;
    float time = floor(iGlobalTime * SPEED * 60.0);
    vec2 uv = fragCoord.xy / iResolution.xy;
    vec3 outCol = texture2D(sTexture, uv).rgb;
    float rnd = random2d(vec2(time, 9545.0));
    vec2 colOffset = vec2(randomRange(vec2(time, 9545.0), 0.0, 0.1),
    randomRange(vec2(time, 7205.0), 0.0, 0.1));
    if (rnd < 0.33){
        outCol.g = texture2D(sTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).g;
    } else if (rnd < 0.66){
        outCol.r = texture2D(sTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).r;
    } else {
        outCol.b = texture2D(sTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).b;
    }
    fragColor = vec4(outCol, 1.0);
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}