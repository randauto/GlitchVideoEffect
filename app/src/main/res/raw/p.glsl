#extension GL_OES_EGL_image_external : require
precision highp float;
varying highp vec2 textureCoordinate;
uniform sampler2D sTexture;
void main() {
    vec2 uv = textureCoordinate.xy;

    if (uv.x >= 0.0 && uv.x <= 0.5) {
        uv.x = uv.x + 0.25;
    } else {
        uv.x = 0.25 - uv.x;
    }

    gl_FragColor = texture2D(sTexture, fract(uv));
}
