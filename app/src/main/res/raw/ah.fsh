#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0+mouse.x/iResolution.x*1.0;
    float amounty = 0.0+mouse.y/iResolution.y*1.0;
    vec2 size = vec2(50.0, 50.0);
    vec2 distortion = vec2((30.0 * amountx), (30.0 * amountx));
    float speed = .75;
    vec2 transformed = vec2(
    fragCoord.x + sin(fragCoord.y / size.x + iGlobalTime * speed) * distortion.x,
    fragCoord.y + cos(fragCoord.x / size.y + iGlobalTime * speed) * distortion.y
    );
    vec2 relCoord = fragCoord.xy / iResolution.xy;
    fragColor = texture2D(sTexture, transformed / iResolution.xy) + vec4(
    (cos(relCoord.x + iGlobalTime * speed * 4.0) + 1.0) / 2.0,
    (relCoord.x + relCoord.y) / 2.0,
    (sin(relCoord.y + iGlobalTime * speed) + 1.0) / 2.0, 0) / (1. / amountx);
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



