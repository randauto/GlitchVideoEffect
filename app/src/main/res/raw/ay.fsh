#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)


void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0+mouse.x/iResolution.x*0.7;
    float amounty = 0.1+mouse.y/iResolution.y*1.0;
    float distance_steps = amountx;
    float linear_steps = 10.+distance_steps*12.;
    vec2 uv = fragCoord.xy / iResolution.xy;
    vec2 dir = vec2(uv - vec2(0.5, 0.5)) * (distance_steps / linear_steps);
    for (float i = 0.01; i < linear_steps; i++) {
        if (
        pow(length(texture2D(sTexture, uv - i * dir).rgb) / 1.4, 0.50)
        > i / (linear_steps)) {
            fragColor = texture2D(sTexture, uv - i * dir) * i / (linear_steps);
        }
    }
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



