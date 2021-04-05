#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;
uniform float iGlobalTime;

#define SPEED 5.0
#define OFFSET .009

vec2 offset(vec2 uv, float amtX, float amtY)
{
    return vec2(uv.x+amtX, uv.y+amtY);
}

vec3 chromatic(vec2 uv)
{
    // offset channels
    float o_x = (1. + sin(iGlobalTime*SPEED)) * OFFSET;

    return vec3(texture2D(sTexture, offset(uv, o_x, 0.)).r,
    texture2D(sTexture, offset(uv, 0., 0.)).g,
    texture2D(sTexture, offset(uv, -o_x, 0.)).b);
}


void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 uv = fragCoord.xy / iResolution.xy;

    vec3 c = chromatic(uv);

    fragColor = vec4(c, 1.0);
}
void main() {
    mainImage(gl_FragColor, textureCoordinate.xy*iResolution.xy);
}



