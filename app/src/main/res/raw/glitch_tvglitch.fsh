
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;
 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;
uniform float               intensity;
 float rand(vec2 co)
{
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);
}

 void main() {

     float horz_wav_strength = 0.15;
     float horz_wav_screen_size = 50.0;
     float horz_wav_vert_size = 100.0;
     float dotted_noise_strength = 0.2;
     float horz_dist_strength = 1.0 / 115.0;

     vec2 uv = textureCoordinate.xy;
     vec2 uv_org = vec2(uv);

     float x=uv.x;
     float y=uv.y;

     // float iTime = 1.0;

     float time360 = mod(iTime, 360.0);
     float time216 = floor(time360*0.6);

     float rand1 = rand(vec2(time360,time360));
     float rand2 = rand(vec2(time360,rand1));

     float rand1xy = rand(vec2(rand1*x,rand2*y));
     float rand2xy = rand(vec2(rand1*y,rand2*x));

     float rand1x = rand(vec2(rand1*x,rand2));
     float rand1y = rand(vec2(rand1*y,rand2));
     float rand2y = rand(vec2(rand1y,rand1y));

     x=uv.x-rand1*rand2*horz_wav_strength*exp(-pow((y-rand1)*horz_wav_vert_size,2.0)/horz_wav_screen_size);
     y=uv.y;

     uv.x=x;
     uv.y=y;

     gl_FragColor = texture2D(iChannel0, uv);
     if (((rand2y/100.0>rand1y))) {
         if ((rand2xy<dotted_noise_strength)) {
             gl_FragColor = vec4(rand1xy, rand1xy, rand1xy,0.0);
         }
     }
     else {
         uv.x = uv.x + rand1y * horz_dist_strength * sin(y*3.141);
         gl_FragColor = texture2D(iChannel0, uv);
     }

        vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }