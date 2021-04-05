#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float iGlobalTime;

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 uv = textureCoordinate.xy;
    vec4 sourceColor = texture2D(sTexture, fract(uv));
    vec2 center = vec2(0.5, 0.5);
    uv -= center;
    uv = uv / iGlobalTime;
    uv += center;
    vec4 scaleColor = texture2D(sTexture, fract(uv));
    fragColor = mix(sourceColor, scaleColor, 0.5 * (0.6 - fract(iGlobalTime)));
}

void main() {
    mainImage(gl_FragColor, textureCoordinate.xy);
}



