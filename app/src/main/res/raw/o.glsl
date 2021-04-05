#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform float uAdditionalColor;
void main()
{
    vec4 color = texture2D(sTexture, textureCoordinate.xy);
    gl_FragColor = vec4(color.r + uAdditionalColor, color.g + uAdditionalColor, color.b + uAdditionalColor, color.a);
}
