#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;
#define iMouse vec2(iResolution.x*1./2., iResolution.y*1./2.)

vec2 barrelDistortion(vec2 p, vec2 amt) {
    p = 2.0 * p - 1.0;
    float maxBarrelPower = sqrt(5.0);
    float radius = dot(p, p);//faster but doesn't match above accurately
    p *= pow(vec2(radius), maxBarrelPower * amt);
    return p * 0.5 + 0.5;
}
vec2 brownConradyDistortion(vec2 uv, float scalar) {
    uv = (uv - 0.5) * 2.0;
    if (true) {
        float barrelDistortion1 = -0.02 * scalar;// K1 in text books
        float barrelDistortion2 = 0.0 * scalar;// K2 in text books
        float r2 = dot(uv, uv);
        uv *= 1.0 + barrelDistortion1 * r2 + barrelDistortion2 * r2 * r2;
    }
    return (uv / 2.0) + 0.5;
}
void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;
    float amountx = 0.0+mouse.x/iResolution.x*50.0;
    float amounty = 0.0+mouse.y/iResolution.y*1.0;
    vec2 uv = fragCoord.xy / iResolution.xy;
    float scalar = 1.0 * amountx;
    vec4 colourScalar = vec4(700.0, 560.0, 490.0, 1.0);
    colourScalar /= max(max(colourScalar.x, colourScalar.y), colourScalar.z);
    colourScalar *= 2.0;
    colourScalar *= scalar;
    vec4 sourceCol = texture2D(sTexture, uv);
    const float numTaps = 8.0;
    gl_FragColor = vec4(0.0);
    for (float tap = 0.0; tap < numTaps; tap += 1.0) {
        fragColor.r += texture2D(sTexture, brownConradyDistortion(uv, colourScalar.r)).r;
        fragColor.g += texture2D(sTexture, brownConradyDistortion(uv, colourScalar.g)).g;
        fragColor.b += texture2D(sTexture, brownConradyDistortion(uv, colourScalar.b)).b;
        colourScalar *= 0.99;
    }
    fragColor /= numTaps;
    fragColor.a = 1.0;
}

void main() {
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}



