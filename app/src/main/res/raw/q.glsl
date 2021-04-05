#extension GL_OES_EGL_image_external : require
precision highp float;
varying highp vec2 textureCoordinate;
uniform sampler2D sTexture;
void main() {
    vec2 uv = textureCoordinate.xy;

    if (uv.y >= 0.0 && uv.y <= 0.5) {
        uv.y = uv.y + 0.25;
    } else {
        uv.y = uv.y - 0.25;
    }

    gl_FragColor = texture2D(sTexture, fract(uv));
}
