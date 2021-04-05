#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0+mouse.x/iResolution.x*1.0;
    float amounty = 0.0-mouse.y/iResolution.y*1.0;
    vec2 uv = fragCoord.xy / iResolution.xy;
    vec4 t = texture2D(sTexture, uv);
    fragColor = smoothstep(vec4(0.0), vec4(0.9), fract(t*(1.6 * 1.6)* (amountx+0.05) * 4.));
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



