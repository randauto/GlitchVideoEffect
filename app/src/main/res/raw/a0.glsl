precision highp float;
uniform sampler2D sTexture;
varying vec2 textureCoordinate;

void main (void) {
    vec2 uv = textureCoordinate.xy;
    float y;
    float x;
    if (uv.y <= 1.0/3.0) {
        y = uv.y * 3.0;
    } else if (uv.y < 2.0/3.0){
        y = (uv.y - 1.0/3.0) * 3.0;
    } else {
        y = (uv.y - 2.0/3.0) * 3.0;
    }

    if (uv.x >=0.0 && uv.x <= 0.5) {
        x = uv.x * 2.0;
    } else {
        x=(uv.x - 1.0/2.0)*2.0;
    }

    gl_FragColor = texture2D(sTexture, vec2(x, y));
}