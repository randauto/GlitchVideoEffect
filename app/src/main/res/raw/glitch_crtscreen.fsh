
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float  intensity;
#define resX 90.0
#define resY 70.0
const vec2 res = vec2(resX, resY);

// Hardness of scanline.
//  -8.0 = soft
// -16.0 = medium
#define hardScan -12.0

// Hardness of pixels in scanline.
// -2.0 = soft
// -4.0 = hard
#define hardPix -10.0

// Hardness of short vertical bloom.
//  -1.0 = wide to the point of clipping (bad)
//  -1.5 = wide
//  -4.0 = not very wide at all
#define hardBloomScan -2.0

// Hardness of short horizontal bloom.
//  -0.5 = wide to the point of clipping (bad)
//  -1.0 = wide
//  -2.0 = not very wide at all
#define hardBloomPix -1.5

// Amount of small bloom effect.
//  1.0/1.0 = only bloom
//  1.0/16.0 = what I think is a good amount of small bloom
//  0.0     = no bloom
#define bloomAmount 1.0/16.0

// Amount of shadow mask.
#define maskDark 0.8
#define maskLight 1.2

//------------------------------------------------------------------------
// sRGB to Linear.
// Assuing using sRGB typed textures this should not be needed.
float ToLinear1(float c){return(c<=0.04045)?c/12.92:pow((c+0.055)/1.055,2.4);}
vec3 ToLinear(vec3 c){return vec3(ToLinear1(c.r),ToLinear1(c.g),ToLinear1(c.b));}

// Linear to sRGB.
// Assuing using sRGB typed textures this should not be needed.
float ToSrgb1(float c){return(c<0.0031308?c*12.92:1.055*pow(c,0.41666)-0.055);}
vec3 ToSrgb(vec3 c){return vec3(ToSrgb1(c.r),ToSrgb1(c.g),ToSrgb1(c.b));}

// Nearest emulated sample given floating point position and texel offset.
// Also zero's off screen.

const vec3 black = vec3(0.0,0.0,0.0);
vec3 Fetch(vec2 pos,vec2 off){
  pos=floor(pos*res+off)/res;
  if(max(abs(pos.x-0.5),abs(pos.y-0.5))>0.5)return black;
  return ToLinear(texture2D(iChannel0,pos.xy,-16.0).rgb);}

// Distance in emulated pixels to nearest texel.
vec2 Dist(vec2 pos) {return -(fract(pos*res)-vec2(0.5));}

// 1D Gaussian.
float Gaus(float pos,float scale){return exp2(scale*pos*pos);}

// 3-tap Gaussian filter along horz line.
vec3 Horz3(vec2 pos,float off){
  vec3 b=Fetch(pos,vec2(-1.0,off));
  vec3 c=Fetch(pos,vec2( 0.0,off));
  vec3 d=Fetch(pos,vec2( 1.0,off));
  float dst=Dist(pos).x;
  // Convert distance to weight.
  float scale=hardPix;
  float wb=Gaus(dst-1.0,scale);
  float wc=Gaus(dst+0.0,scale);
  float wd=Gaus(dst+1.0,scale);
  // Return filtered sample.
  return (b*wb+c*wc+d*wd)/(wb+wc+wd);}

// 5-tap Gaussian filter along horz line.
vec3 Horz5(vec2 pos,float off){
  vec3 a=Fetch(pos,vec2(-2.0,off));
  vec3 b=Fetch(pos,vec2(-1.0,off));
  vec3 c=Fetch(pos,vec2( 0.0,off));
  vec3 d=Fetch(pos,vec2( 1.0,off));
  vec3 e=Fetch(pos,vec2( 2.0,off));
  float dst=Dist(pos).x;
  // Convert distance to weight.
  float scale=hardPix;
  float wa=Gaus(dst-2.0,scale);
  float wb=Gaus(dst-1.0,scale);
  float wc=Gaus(dst+0.0,scale);
  float wd=Gaus(dst+1.0,scale);
  float we=Gaus(dst+2.0,scale);
  // Return filtered sample.
  return (a*wa+b*wb+c*wc+d*wd+e*we)/(wa+wb+wc+wd+we);}

// 7-tap Gaussian filter along horz line.
vec3 Horz7(vec2 pos,float off){
  vec3 a=Fetch(pos,vec2(-3.0,off));
  vec3 b=Fetch(pos,vec2(-2.0,off));
  vec3 c=Fetch(pos,vec2(-1.0,off));
  vec3 d=Fetch(pos,vec2( 0.0,off));
  vec3 e=Fetch(pos,vec2( 1.0,off));
  vec3 f=Fetch(pos,vec2( 2.0,off));
  vec3 g=Fetch(pos,vec2( 3.0,off));
  float dst=Dist(pos).x;
  // Convert distance to weight.
  float scale=hardBloomPix;
  float wa=Gaus(dst-3.0,scale);
  float wb=Gaus(dst-2.0,scale);
  float wc=Gaus(dst-1.0,scale);
  float wd=Gaus(dst+0.0,scale);
  float we=Gaus(dst+1.0,scale);
  float wf=Gaus(dst+2.0,scale);
  float wg=Gaus(dst+3.0,scale);
  // Return filtered sample.
  return (a*wa+b*wb+c*wc+d*wd+e*we+f*wf+g*wg)/(wa+wb+wc+wd+we+wf+wg);}

// 5-tap Gaussian filter along horz line.
vec3 Horz5Bloom(vec2 pos,float off){
  vec3 b=Fetch(pos,vec2(-2.0,off));
  vec3 c=Fetch(pos,vec2(-1.0,off));
  vec3 d=Fetch(pos,vec2( 0.0,off));
  vec3 e=Fetch(pos,vec2( 1.0,off));
  vec3 f=Fetch(pos,vec2( 2.0,off));
  float dst=Dist(pos).x;
  // Convert distance to weight.
  float scale=hardBloomPix;
  float wb=Gaus(dst-2.0,scale);
  float wc=Gaus(dst-1.0,scale);
  float wd=Gaus(dst+0.0,scale);
  float we=Gaus(dst+1.0,scale);
  float wf=Gaus(dst+2.0,scale);
  // Return filtered sample.
  return (b*wb+c*wc+d*wd+e*we+f*wf)/(wb+wc+wd+we+wf);}

// Return scanline weight.
float Scan(vec2 pos,float off){
  float dst=Dist(pos).y;
  return Gaus(dst+off,hardScan);}

// Return scanline weight for bloom.
float BloomScan(vec2 pos,float off){
  float dst=Dist(pos).y;
  return Gaus(dst+off,hardBloomScan);}

// Allow nearest three lines to effect pixel.
vec3 Tri(vec2 pos){
  vec3 a=Horz3(pos,-1.0);
  vec3 b=Horz5(pos, 0.0);
  vec3 c=Horz3(pos, 1.0);
  float wa=Scan(pos,-1.0);
  float wb=Scan(pos, 0.0);
  float wc=Scan(pos, 1.0);
  return a*wa+b*wb+c*wc;}

// Small bloom.
vec3 Bloom(vec2 pos){
  vec3 a=Horz5(pos,-2.0);
  vec3 b=Horz7(pos,-1.0);
  vec3 c=Horz7(pos, 0.0);
  vec3 d=Horz7(pos, 1.0);
  vec3 e=Horz5(pos, 2.0);
  float wa=BloomScan(pos,-2.0);
  float wb=BloomScan(pos,-1.0);
  float wc=BloomScan(pos, 0.0);
  float wd=BloomScan(pos, 1.0);
  float we=BloomScan(pos, 2.0);
  return a*wa+b*wb+c*wc+d*wd+e*we;}

// Very compressed TV style shadow mask.
vec3 Mask1(vec2 pos){
  float line=maskLight;
  float odd=0.0;
  if(fract(pos.x/6.0)<0.5)odd=1.0;
  if(fract((pos.y+odd)/2.0)<0.5)line=maskDark;
  pos.x=fract(pos.x/3.0);
  vec3 mask=vec3(maskDark,maskDark,maskDark);
  if(pos.x<0.333)mask.r=maskLight;
  else if(pos.x<0.666)mask.g=maskLight;
  else mask.b=maskLight;
  mask*=line;
  return mask;}

// Aperture-grille.
vec3 Mask2(vec2 pos){
  pos.x=fract(pos.x/3.0);
  vec3 mask=vec3(maskDark,maskDark,maskDark);
  if(pos.x<0.333)mask.r=maskLight;
  else if(pos.x<0.666)mask.g=maskLight;
  else mask.b=maskLight;
  return mask;}

// Stretched VGA style shadow mask (same as prior shaders).
vec3 Mask3(vec2 pos){
  pos.x+=pos.y*3.0;
  vec3 mask=vec3(maskDark,maskDark,maskDark);
  pos.x=fract(pos.x/6.0);
  if(pos.x<0.333)mask.r=maskLight;
  else if(pos.x<0.666)mask.g=maskLight;
  else mask.b=maskLight;
  return mask;}

// VGA style shadow mask.
vec3 Mask4(vec2 pos){
  pos.xy=floor(pos.xy*vec2(1.0,0.5));
  pos.x+=pos.y*3.0;
  vec3 mask=vec3(maskDark,maskDark,maskDark);
  pos.x=fract(pos.x/6.0);
  if(pos.x<0.333)mask.r=maskLight;
  else if(pos.x<0.666)mask.g=maskLight;
  else mask.b=maskLight;
  return mask;}


// Entry.
void main(){
  vec2 fragCoord = iResolution.xy * textureCoordinate;
  vec2 pos=fragCoord.xy/iResolution.xy;
  fragColor.rgb=Tri(pos)*Mask4(fragCoord.xy);
  fragColor.rgb+=Bloom(pos)*bloomAmount;
  fragColor.a=1.0;
  fragColor.rgb=ToSrgb(fragColor.rgb);

  vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}



