precision highp float;
varying vec2 textureCoordinate;
 uniform sampler2D iChannel0;
 uniform sampler2D waterMark;
 uniform vec2                iMouse; 
uniform float               intensity;
#define wmW iMouse.x
 #define wmH iMouse.y
 void main() {
     vec2 uv = textureCoordinate;
     if(uv.x >= (1.0 - wmW) && uv.y <= wmH){
      vec2 pos;
      pos.x = uv.x - (1.0 - wmW);
      pos.y = uv.y;
      gl_FragColor = texture2D(waterMark, pos);
     }else{
     gl_FragColor = texture2D(iChannel0, uv);
     }
            vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
 }