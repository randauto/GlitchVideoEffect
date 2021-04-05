#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D sTexture;
//修改这个值，可以控制曝光的程度
uniform float uAdditionalColor;
void main()
{
vec4 color = texture2D(sTexture,textureCoordinate.xy);
gl_FragColor = vec4(color.r + uAdditionalColor - 1.0,color.r + uAdditionalColor - 1.0,color.r + uAdditionalColor - 1.0,color.a);
}