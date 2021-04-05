precision highp float;

uniform vec3                iResolution;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

const highp vec3 transMatrix = vec3(0.2125, 0.7154, 0.0721);
const vec4 bgColor = vec4(0.5, 0.5, 0.5, 1.0);

void mainImage( out vec4 fragColor, in vec2 fragCoord )
  {
      vec2 currentUV = fragCoord;
      vec2 preUV = vec2(currentUV.x-5.0/iResolution.x, currentUV.y-5.0/iResolution.y);
      vec4 currentMask = texture2D(sTexture, currentUV);
      vec4 preMask = texture2D(sTexture, preUV);
      vec4 delColor = currentMask - preMask;
      float luminance = dot(delColor.rgb, transMatrix);
      fragColor = vec4(vec3(luminance), 0.0) + bgColor;
   }

void main() {
 	mainImage(gl_FragColor, textureCoordinate);
 }
