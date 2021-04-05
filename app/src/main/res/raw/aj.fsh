#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0-mouse.x/iResolution.x*1.0;
    float amounty = 0.0-mouse.y/iResolution.y*0.05;
    vec4 fg = vec4(0.08, 0.07, 0.08, 1.0);
    vec4 bg = vec4(0.5, 0.14, 0.1, 1.0);
    vec2 uv = fragCoord.xy / iResolution.xy;
    float t1 = fract(amounty);
    if (sin(1.0 + t1 * 3.0 + tan(gl_FragCoord.y * (amounty*0.4*sin(iGlobalTime/5.))) - sqrt(gl_FragCoord.y * t1)) >= 0.7){
        float y = uv.y * 12.0;
        if (y - floor(y) >= 0.5){
            uv.x += amountx*0.1;
        } else {
            uv.x -= amountx*0.1;
        }
    }
    fragColor.r = texture2D(sTexture, uv+vec2(amountx*0.04, 0.0)).r;
    fragColor.gb = texture2D(sTexture, uv).gb;
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



