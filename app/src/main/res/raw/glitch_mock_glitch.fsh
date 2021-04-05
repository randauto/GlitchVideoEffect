
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
#define PI 3.141592

float random(vec2 p){
    float q = dot(p,vec2(127.1,311.7));
    return fract(sin(q)*437.53);
}
vec4 glitch(vec2 p){
    float b=0.5;
    vec4 c=texture(iChannel0,p);
   float t=iTime-mod(iTime,0.3);
    vec2 q=p-mod(p,b);
    for(float i=0.0;i<15.0;i++){
       if(random(q)>0.3){
           q=p-mod(p,b);
       }else{
           break;
       }
        b*=random(vec2(mod(iTime,1.5)))<0.3?1.0:clamp(sin(t/10.0-5.5),0.65-random(vec2(t/10.0-5.5)),0.65+random(vec2(t/10.0-5.5)));
      // b*=0.65;
    }

    c.a-=random(vec2(mod(iTime,1.5)))<0.3?0.0:0.3*random(q);
    c.rgb+=random(vec2(mod(iTime,1.5)))<0.3?vec3(0.0):vec3(random(q)*2.0,0.2,random(vec2(q.y,q.x))*2.0-1.0);
    c.rgb-=random(vec2(mod(iTime,1.5)))<0.3 && random(q)<0.01 ? vec3(0.0):texture(iChannel0,p+vec2(random(q)*2.0-1.0,0.0)).rgb;
    c.xyz-=random(vec2(mod(iTime,1.5)))<0.3?vec3(0.0):vec3(0.3*random(vec2(0.0,p.y+iTime/10.0)));
    c.xyz-=random(vec2(0.0,p.y-iTime/5.0-mod(p.y-iTime/5.0,0.02)))>0.9?texture(iChannel0,p+vec2(0.0,random(q))).rgb:vec3(0.0);
    c.rgb*=texture(iChannel0,p-vec2(sin(p.y*50.0)/5.0*sin(iTime*3.0*p.y),-iTime/10.0)).rgb;
    return c;
}
void main(){
    vec2 p=vec2(textureCoordinate.x,textureCoordinate.y);
    vec4 color=glitch(p);
    fragColor = color;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


