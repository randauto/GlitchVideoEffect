#extension GL_OES_EGL_image_external : require
precision highp float;
uniform sampler2D sTexture;
varying vec2 textureCoordinate;

void main (void) {
    vec2 uv = textureCoordinate.xy;
    float y;
    if (uv.y < 1.0/3.0) {
        y = uv.y + 1.0/3.0;
    } else if (uv.y > 2.0/3.0){
        y = uv.y - 1.0/3.0;
    } else {
        y =  uv.y;
    }

    gl_FragColor = texture2D(sTexture, vec2(uv.x, y));
}