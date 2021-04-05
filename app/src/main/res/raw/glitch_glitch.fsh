
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
varying vec2 textureCoordinate;
uniform sampler2D iChannel0;
uniform  float iTime;
uniform  vec3 iResolution;
uniform float               intensity;
 float rand( vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
}
  float hash( vec2 _v ){
     return fract( sin( dot( _v, vec2( 89.44, 19.36 ) ) ) * 22189.22 );
 }
  float iHash(  vec2 _v,  vec2 _r ){
      float h00 = hash( vec2( floor( _v * _r + vec2( 0.0, 0.0 ) ) / _r ) );
      float h10 = hash( vec2( floor( _v * _r + vec2( 1.0, 0.0 ) ) / _r ) );
      float h01 = hash( vec2( floor( _v * _r + vec2( 0.0, 1.0 ) ) / _r ) );
      float h11 = hash( vec2( floor( _v * _r + vec2( 1.0, 1.0 ) ) / _r ) );
      vec2 ip = vec2( smoothstep( vec2( 0.0, 0.0 ), vec2( 1.0, 1.0 ), mod( _v*_r, 1. ) ) );
     return ( h00 * ( 1. - ip.x ) + h10 * ip.x ) * ( 1. - ip.y ) + ( h01 * ( 1. - ip.x ) + h11 * ip.x ) * ip.y;
 }
  float noise(  vec2 _v ){
      float sum = 0.;
     for( int i=1; i<9; i++ )
     {
         sum += iHash( _v + vec2( i ), vec2( 2. * pow( 2., float( i ) ) ) ) / pow( 2., float( i ) );
     }
     return sum;
 }
 const lowp vec3 warmFilter = vec3(0.93, 0.54, 0.0);
 const highp mat3 RGBtoYIQ = mat3(0.299, 0.587, 0.114, 0.596, -0.274, -0.322, 0.212, -0.523, 0.311);
 const highp mat3 YIQtoRGB = mat3(1.0, 0.956, 0.621, 1.0, -0.272, -0.647, 1.0, -1.105, 1.702);
  vec4 whiteBalance( vec4 source, float a) {
      float tint = 0.0;
     highp vec3 yiq = RGBtoYIQ * source.rgb; //adjusting tint
     yiq.b = clamp(yiq.b + tint*0.5226*0.1, -0.5226, 0.5226);
     lowp vec3 rgb = YIQtoRGB * yiq;

     lowp vec3 processed = vec3(
                                (rgb.r < 0.5 ? (2.0 * rgb.r * warmFilter.r) : (1.0 - 2.0 * (1.0 - rgb.r) * (1.0 - warmFilter.r))), //adjusting temperature
                                (rgb.g < 0.5 ? (2.0 * rgb.g * warmFilter.g) : (1.0 - 2.0 * (1.0 - rgb.g) * (1.0 - warmFilter.g))),
                                (rgb.b < 0.5 ? (2.0 * rgb.b * warmFilter.b) : (1.0 - 2.0 * (1.0 - rgb.b) * (1.0 - warmFilter.b))));

     return vec4(mix(rgb, processed, a), source.a);
 }
 void main() {
      vec2 uv = textureCoordinate;
      float time = iTime;
      float t = mod(iTime,6.0);
     if (t <= 5.0) {
          float dt = rand(vec2(t,t*t));
         if (dt > 0.8) {
             uv.y += (dt - 0.8) * 0.1;
             uv.x -= (dt - 0.8) * 0.05 * rand(vec2(t));
             fragColor = texture(iChannel0,uv);
             return;
         }
     }
     uv.y += (noise( vec2( uv.x, time ) ) - 0.5 )* 0.005;
     uv.y += (noise( vec2( uv.x * 100.0, time * 10.0 ) ) - 0.5 ) * 0.01;
     fragColor = texture(iChannel0,uv);
      vec2 uv_r = uv;
      vec2 uv_g = uv;
      vec2 uv_b = uv;
      vec2 dist = sin((vec2(time*0.5))) * 0.15;
     if (dist.x > 0.0)  {
         uv_r += dist * 0.12;
         uv_b += dist * 0.06;
     } else {
         uv_r.y += dist.y * 0.08;
         uv_b += dist * 0.1;
     }
     fragColor.r = texture(iChannel0, uv_r).r;
     fragColor.g = texture(iChannel0, uv_g).g;
     fragColor.b = texture(iChannel0,uv_b).b;
     gl_FragColor = whiteBalance(gl_FragColor, 0.030);


     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }