#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./10., iResolution.y*1./10.)

float rand(vec2 co){
    return fract(sin(dot(co.xy + iGlobalTime,vec2(12.0 ,78.0)))) * 1.0;
}
void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0+mouse.x/iResolution.x*0.5;
    float amounty = 0.0-mouse.y/iResolution.y*60.;
    float ring1 = amounty;
    float ring2 = amounty/2.;
    float push1 = 5.4;
    float push2 = 10.0;
    float diminish = 0.05;
    vec2 uv = fragCoord.xy / iResolution.xy;
    float r1 = rand(floor(uv.yy*ring1 )/ring1);
    float r2 = rand(floor(uv.yy*ring2 )/ring2);
    r1 = -1.0 + 2.0 * r1;
    r2 = -1.0 + 2.0 * r2;
    r1 *= (push1 * amountx);
    r2 *= (push2 * amountx);
    r1 += r2;
    r1 *= diminish;
    vec4 tex = texture2D(sTexture, uv + vec2(r1,0.0));
    if(uv.x+r1 > (1.0 - amountx) || uv.x+r1 <= (amountx)){
        fragColor = vec4(vec3(0.0),1.0);
    } else {
        fragColor =tex;
    }
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



