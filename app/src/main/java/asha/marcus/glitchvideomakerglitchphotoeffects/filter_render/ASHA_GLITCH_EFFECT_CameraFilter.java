package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_CameraFilter extends FilterRender {
    public ASHA_GLITCH_EFFECT_CameraFilter() {
        setFragmentShader("precision lowp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nvoid main() {\nvec2 uv = textureCoordinate.xy;\ngl_FragColor = texture2D(inputImageTexture, uv);\n}");
    }
}
