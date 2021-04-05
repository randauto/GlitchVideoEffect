
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
#define T iTime
#define R iResolution.xy
#define S(a, b, c) smoothstep(a, b, c)
#define PI acos(-1.)
#define CEL rem(R)
#define LOWRES 320.

 float rem( vec2 iR)
{
    float slices = floor(iR.y / LOWRES);
    if(slices < 1.){
        return 4.;
    }
    else if(slices == 1.){
        return 6.;
    }
    else if(slices == 2.){
        return 8.;
    }
    else if(slices >= 3.){
        return 10.;
    }
    else if(slices >= 4.){
        return 12.;
    }
}

 /////////////////////////////////////////////
 // hash2 taken from Dave Hoskins
 // https://www.shadertoy.com/view/4djSRW
 /////////////////////////////////////////////

 float hash2( vec2 p)
{
     vec3 p3  = fract(vec3(p.xyx) * .2831);
    p3 += dot(p3, p3.yzx + 19.19);
    return fract((p3.x + p3.y) * p3.z);
}

 void main() {
      vec2 uv = textureCoordinate;

     // sound amplitude
     float amp = .0;
     amp += .5;


     // glitch offset
      vec2 V  = 1. - 2. * uv;
      vec2 off = vec2(S(.0, amp * CEL * .5, cos(T + uv.y  *5.0 )), .0) - vec2(.5, .0);

     // colorize
     float r = texture(iChannel0, .03 * off + uv).x;
     float g = texture(iChannel0, .04 * off + uv).x;
     float b = texture(iChannel0, .05 * off + uv).x;
     fragColor = vec4(.0,.1,.2,1.);

     fragColor += .06 * hash2(T + V * vec2(1462.439, 297.185));  // animated grain (hash2 function in common tab)
     fragColor += vec4(r, g, b, 1.);
     fragColor *= 1.25 *vec4(1. - S(.1, 1.8, length(V * V))); // vigneting

     float fragCoordy = mod(uv * R,  CEL).y;
     fragColor *= .4+sign(S(.99, 1., fragCoordy));
     fragColor += .14 * vec4(pow(1. - length(V*vec2(.5, .35)), 3.), .0,.0,1.);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }