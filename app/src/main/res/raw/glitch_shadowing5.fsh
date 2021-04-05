
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
#define fragCoord textureCoordinate
 precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
 void main() {

      vec2 uv = textureCoordinate;

           float PI = 3.1415926;

          // 偏移的角度
          float rAngle = 5.0 * PI / 8.0;
          float gAngle = PI / 2.0;
          float bAngle = 3.0 * PI / 8.0;

          // 偏移比例，取值范围【0， 1】，作用见下一个注释
          float rOffsetFactor = 0.2;
          float gOffsetFactor = 0.0;
          float bOffsetFactor = 0.2;

          // amount:随时间变化的偏移距离, 真正的偏移距离=amount * xOffsetFactor;
      	float amount = 0.0;

      	amount = (1.0 + sin(iTime*6.0)) * 0.5;
      	amount *= 1.0 + sin(iTime*16.0) * 0.5;
      	amount *= 1.0 + sin(iTime*19.0) * 0.5;
      	amount *= 1.0 + sin(iTime*27.0) * 0.5;
      	amount = pow(amount, 3.0);

      	amount *= 0.05;

          float rxOffset = amount * rOffsetFactor * cos(rAngle);
                         float ryOffset = amount * rOffsetFactor * sin(rAngle);
                         float gxOffset = amount * gOffsetFactor * cos(gAngle);
                         float gyOffset = amount * gOffsetFactor * sin(gAngle);
                         float bxOffset = amount * bOffsetFactor * cos(bAngle);
                         float byOffset = amount * bOffsetFactor * sin(bAngle);

          vec3 col;
          col.r = texture( iChannel0, vec2(uv.x+rxOffset,uv.y+ryOffset) ).r;
          col.g = texture( iChannel0, vec2(uv.x+gxOffset, uv.y+gyOffset) ).g;
          col.b = texture( iChannel0, vec2(uv.x+bxOffset,uv.y+byOffset) ).b;

      	col *= (1.0 - amount * 0.5);

          fragColor = vec4(col,1.0);

            vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
 }