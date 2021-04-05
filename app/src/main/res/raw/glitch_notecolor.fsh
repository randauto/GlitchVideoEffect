
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;
uniform float intensity;
#define freqStart -1.0
#define freqInterval 0.1
#define sampleSize 0.02           // How accurately to sample spectrum, must be a factor of 1.0

 float rand( vec2 co) {
     return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
 }

 void main() {

      vec2 uv = textureCoordinate;

     // first texture row is frequency data
     // sample intensities in frequency interval.

    //  float intensity = 0.0;
     for(float s = 0.0; s < freqInterval; s += freqInterval * sampleSize) {
         intensity += rand(vec2(fract(iTime)));
     }
     intensity = abs(intensity);
     intensity = pow((intensity*sampleSize),3.0)*4.0;


     //set offsets
      vec2 rOffset = vec2(-0.02,0.0)*intensity;
      vec2 gOffset = vec2(0.0,0.0)*intensity;
      vec2 bOffset = vec2(0.04,0.0)*intensity;

      vec4 rValue = texture(iChannel0, uv - rOffset);
      vec4 gValue = texture(iChannel0, uv - gOffset);
      vec4 bValue = texture(iChannel0, uv - bOffset);

     fragColor = vec4(rValue.r, gValue.g, bValue.b, 1.0);

    //  vec2 uvori = textureCoordinate.xy;
    // vec4 texori = texture2D(iChannel0, uvori);
    // gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }
