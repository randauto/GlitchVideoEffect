#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES sTexture;
varying vec2 vTexCoord;

void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec4 mask = texture2D(sTexture, fragCoord);
    float color = (mask.r + mask.g + mask.b) / 3.0;
    color = step(0.5, color);
    vec4 tempColor = vec4(color, color, color, 1.0);
    fragColor = tempColor;
}

void main() {
    mainImage(gl_FragColor, vTexCoord);
}