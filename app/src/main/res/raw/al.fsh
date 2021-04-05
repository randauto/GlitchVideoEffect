#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;
uniform int type;

void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 uv = fragCoord.xy/iResolution.xy;
    if (type==0){
        uv.x = abs(uv.x - 0.5) + 0.5;
    } else if (type==1){
        uv.x   = abs(uv.x - 0.5) + 0.;
    } else if (type==2){
        uv.y = abs(uv.y - 0.5) + 0.5;
    } else {
        uv.y = abs(uv.y - 0.5) + 0.0;
    }
    vec4 image1 = texture2D(sTexture, uv);
    fragColor = image1;
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



