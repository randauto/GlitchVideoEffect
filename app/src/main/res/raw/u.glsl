#extension GL_OES_EGL_image_external : require
precision highp float;
uniform sampler2D sTexture;
varying vec2 textureCoordinate;

void main(){
    vec2 uv = textureCoordinate;
    if (uv.x < 1.0/3.0) {
        uv.x = uv.x + 1.0/3.0;
    } else if (uv.x > 2.0/3.0){
        uv.x = uv.x  - 1.0/3.0;
    }
    vec4 mask = texture2D(sTexture, uv);
    gl_FragColor = vec4(mask.rgb, 1.0);
}