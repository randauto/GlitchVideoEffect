
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 //  vec2 fragCoord = textureCoordinate * iResolution;


#define Sample 5.
#define _2PI 6.2831853072

 float rand(float id){
     return fract(sin(id*400.) * 4375.5453);
 }

  vec2 blur( vec2 u)
{
     vec3 col = vec3(0.);
    for (float i = 0.; i < Sample; ++i) {
        float rad = i/Sample*_2PI;
         vec2 offset = vec2(cos(rad),sin(rad)) ;
        offset *= rand(u.x+u.y);
        u -= offset*0.015;
    }
    return u;
    //    if(iMouse.z>0.){
    //        f = texture(iChannel1,u);
    //    }
    //    else{
    //        f = texture(iChannel0,u);
    //    }
}

 void main() {
      vec2 uv = textureCoordinate;

     float time = (0.5 - abs(mod(iTime, 1.) - 0.5) - 0.1) * 30.;
     for (float i = 0.; i < time; ++i) {
         uv = blur(uv);
     }
     fragColor = texture(iChannel0,uv);

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }