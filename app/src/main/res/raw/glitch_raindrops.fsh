
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
/*

 A quick experiment with rain drop ripples.

 This effect was written for and used in the launch scene of the
 64kB intro "H - Immersion", by Ctrl-Alt-Test.

 > http://www.ctrl-alt-test.fr/?page_id=444
 > https://www.youtube.com/watch?v=27PN1SsXbjM

 --
 Zavie / Ctrl-Alt-Test

 */

 // Maximum number of cells a ripple can cross.
#define MAX_RADIUS 2

 // Set to 1 to hash twice. Slower, but less patterns.
#define DOUBLE_HASH 1

 // Hash functions shamefully stolen from:
 // https://www.shadertoy.com/view/4djSRW
#define HASHSCALE1 .1031
#define HASHSCALE3 vec3(.1031, .1030, .0973)

 float hash12( vec2 p)
{
     vec3 p3  = fract(vec3(p.xyx) * HASHSCALE1);
    p3 += dot(p3, p3.yzx + 19.19);
    return fract((p3.x + p3.y) * p3.z);
}

  vec2 hash22( vec2 p)
{
     vec3 p3 = fract(vec3(p.xyx) * HASHSCALE3);
    p3 += dot(p3, p3.yzx+19.19);
    return fract((p3.xx+p3.yz)*p3.zy);

}

 void main() {

     float resolution = 10. * exp2(-3.*0.1);
      vec2 uv = textureCoordinate * vec2(iResolution.x / iResolution.y,1.0) * resolution;
      vec2 p0 = floor(uv);

      vec2 circles = vec2(0.);
     for (int j = -MAX_RADIUS; j <= MAX_RADIUS; ++j)
     {
         for (int i = -MAX_RADIUS; i <= MAX_RADIUS; ++i)
         {
              vec2 pi = p0 + vec2(i, j);
#if DOUBLE_HASH
              vec2 hsh = hash22(pi);
#else
              vec2 hsh = pi;
#endif
              vec2 p = pi + hash22(hsh);

             float t = fract(0.3*iTime + hash12(hsh));
              vec2 v = p - uv;
             float d = length(v) - (float(MAX_RADIUS) + 1.)*t;

             float h = 1e-3;
             float d1 = d - h;
             float d2 = d + h;
             float p1 = sin(31.*d1) * smoothstep(-0.6, -0.3, d1) * smoothstep(0., -0.3, d1);
             float p2 = sin(31.*d2) * smoothstep(-0.6, -0.3, d2) * smoothstep(0., -0.3, d2);
             circles += 0.5 * normalize(v) * ((p2 - p1) / (2. * h) * (1. - t) * (1. - t));
         }
     }
     circles /= float((MAX_RADIUS*2+1)*(MAX_RADIUS*2+1));

     float intensityl = mix(0.01, 0.15, smoothstep(0.1, 0.6, abs(fract(0.05*iTime + 0.5)*2.-1.)));
      vec3 n = vec3(circles, sqrt(1. - dot(circles, circles)));
      vec3 color = texture(iChannel0, textureCoordinate - intensityl*n.xy).rgb + 5.*pow(clamp(dot(n, normalize(vec3(1., 0.7, 0.5))), 0., 1.), 6.);
     fragColor = vec4(color, 1.0);

      vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }