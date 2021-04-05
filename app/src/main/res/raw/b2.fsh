#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 uv = textureCoordinate.xy;
    vec2 scaleCoordinate = vec2((iGlobalTime - 1.0) * 0.5 + uv.x / iGlobalTime, (iGlobalTime - 1.0) * 0.5 + uv.y / iGlobalTime);
    vec4 smoothColor = texture2D(sTexture, scaleCoordinate);
    vec4 shiftRedColor = texture2D(sTexture, scaleCoordinate + vec2(-0.1 * (iGlobalTime - 1.0), - 0.1 *(iGlobalTime - 1.0)));
    vec4 shiftGreenColor = texture2D(sTexture, scaleCoordinate + vec2(-0.075 * (iGlobalTime - 1.0), - 0.075 *(iGlobalTime - 1.0)));
    vec4 shiftBlueColor = texture2D(sTexture, scaleCoordinate + vec2(-0.05 * (iGlobalTime - 1.0), - 0.05 *(iGlobalTime - 1.0)));
    vec3 resultColor = vec3(shiftRedColor.r, shiftGreenColor.g, shiftBlueColor.b);
    fragColor = vec4(resultColor, smoothColor.a);
}

void main() {
    mainImage(gl_FragColor, textureCoordinate.xy);
}



