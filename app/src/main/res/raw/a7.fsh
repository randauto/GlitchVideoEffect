#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying highp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform highp mat4 uSTMatrix;
uniform float iGlobalTime;
uniform vec3 iResolution;

#define iMouse vec2(iResolution.x*1./3., iResolution.y*1./3.)

float random2d(vec2 n) {
    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);
}
void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.01+mouse.x/iResolution.x*1.;
    float amounty = 0.01+mouse.y/iResolution.y*1.;
    vec2 uv = fragCoord.xy/iResolution.xy;
    vec2 uv1 = fragCoord.xy/iResolution.xy;
    float time = floor(iGlobalTime*20.);
    float rnd = random2d(vec2(time, 10.));
    for (float i = 0.0; i < 1.; i += 0.2) {
        if (uv1.y<random2d(vec2(time, i))&&uv1.y>random2d(vec2(time, i+12.))){
            uv1.x -= amountx*0.1;
            uv1.y = uv.y+((rnd+i)/20.)*amountx;
        }
    }
    vec4 col;
    if (rnd <= 0.33) {
        col.r = texture2D(sTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).r;
        col.gb = texture2D(sTexture, uv1).gb;
    } else if (rnd <= 0.66) {
        col.g = texture2D(sTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).g;
        col.rb = texture2D(sTexture, uv1).rb;
    } else if (rnd <= 1.0) {
        col.b = texture2D(sTexture, uv1+vec2((amountx*0.1)*rnd, 0.)).b;
        col.rg = texture2D(sTexture, uv1).rg;
    }
    fragColor = col;
}

//void mainImage(out vec4 fragColor, in vec2 fragCoord)
//{
//    vec2 uv = fragCoord.xy;
//
//    vec3 tex = texture2D(sTexture, uv).rgb;
//    float shade = dot(tex, vec3(0.333333));
//
//    vec3 col = mix(vec3(0.1, 0.36, 0.8) * (1.0-2.0*abs(shade-0.5)), vec3(1.06, 0.8, 0.55), 1.0-shade);
//    fragColor =vec4(col, 1.0)+mainImage1(fragCoord);
//}

void main() {
//    highp vec2 tx_transformed = (uSTMatrix * vec4(vTexCoord, 0, 1.0)).xy;
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



