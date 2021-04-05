#extension GL_OES_standard_derivatives : enable
precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    float POWER = 0.04; // How much the effect can spread horizontally
    float VERTICAL_SPREAD = 7.0; // How vertically is the sin wave spread
    float ANIM_SPEED = 0.4; // Animation speed

    vec2 uv = fragCoord.xy / iResolution.xy;
    float y = (uv.y + iGlobalTime * ANIM_SPEED) * VERTICAL_SPREAD;

    uv.x += (
    // This is the heart of the effect, feel free to modify
    // The sin functions here or add more to make it more complex
    // and less regular
    sin(y)
    + sin(y * 10.0) * 0.2
    + sin(y * 50.0) * 0.03
    )
    * POWER // Limit by maximum spread
    * sin(uv.y * 3.14) // Disable on edges / make the spread a bell curve
    * sin(iGlobalTime); // And make the power change in time

    fragColor = texture2D(sTexture, uv);
}
void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
