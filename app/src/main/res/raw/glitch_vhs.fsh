
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
varying vec2 textureCoordinate;
 uniform sampler2D iChannel0;
 uniform float iTime;
 uniform float               intensity;
 float rand( vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
 }
 float hash( vec2 _v ){
     return fract( sin( dot( _v, vec2( 89.44, 19.36 ) ) ) * 22189.22 );
 }
 float iHash( vec2 _v, vec2 _r ){
     float h00 = hash( vec2( floor( _v * _r + vec2( 0.0, 0.0 ) ) / _r ) );
     float h10 = hash( vec2( floor( _v * _r + vec2( 1.0, 0.0 ) ) / _r ) );
     float h01 = hash( vec2( floor( _v * _r + vec2( 0.0, 1.0 ) ) / _r ) );
     float h11 = hash( vec2( floor( _v * _r + vec2( 1.0, 1.0 ) ) / _r ) );
     vec2 ip = vec2( smoothstep( vec2( 0.0, 0.0 ), vec2( 1.0, 1.0 ), mod( _v*_r, 1. ) ) );
     return ( h00 * ( 1. - ip.x ) + h10 * ip.x ) * ( 1. - ip.y ) + ( h01 * ( 1. - ip.x ) + h11 * ip.x ) * ip.y;
 }
 float noise( vec2 _v ){
     float sum = 0.;
     for( int i=1; i<9; i++ )
     {
         sum += iHash( _v + vec2( i ), vec2( 2. * pow( 2., float( i ) ) ) ) / pow( 2., float( i ) );
     }
     return sum;
 }
 void main() {
     vec2 uv = textureCoordinate;
     float time = iTime;
     float t = mod(iTime,4.0);
     if (t >= 2.0) {
         float dt = t-2.0;
         if (dt < 1.0) {
             uv.y = fract(uv.y+ dt * 1.5);
         } else if (dt < 1.5) {
             uv.y = fract(uv.y+ 1.0 * 1.5 - (dt-1.0)*2.0);
         } else {
             uv.y = fract(uv.y+ 1.0 * 1.5 - (1.5-1.0)*2.0 + (dt - 1.5) * 3.0);
         }
     }
     uv.x += (noise( vec2( uv.y, time ) ) - 0.5 )* 0.005;
     uv.x += (noise( vec2( uv.y * 100.0, time * 10.0 ) ) - 0.5 ) * 0.02;
     float horz_wav_strength = 0.15;
     float horz_wav_screen_size = 10.0;
     float horz_wav_vert_size = 100.0;
     float rand1 = rand(vec2(iTime));
     float rand2 = rand(vec2(iTime));
     for ( int i = 0;i<4;i++) {
         rand1 = rand2;
         rand2 = rand(vec2(iTime,rand1));
         uv.x += rand1*rand2*horz_wav_strength*exp(-pow((uv.y-rand1)*horz_wav_vert_size,2.0)/horz_wav_screen_size);
     }
     fragColor = texture(iChannel0,uv);
     vec2 uv_r = uv;
     vec2 uv_g = uv;
     vec2 uv_b = uv;
     vec2 dist = sin((vec2(time*0.5))) * 0.15;
     if (dist.x > 0.0) {
         uv_r += dist * 0.12;
         uv_b += dist * 0.06;
     } else {
         uv_r.y += dist.y * 0.08;
         uv_b += dist * 0.1;
     }
     fragColor.r = texture(iChannel0, uv_r).r;
     fragColor.g = texture(iChannel0, uv_g).g;
     fragColor.b = texture(iChannel0,uv_b).b;

          vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }