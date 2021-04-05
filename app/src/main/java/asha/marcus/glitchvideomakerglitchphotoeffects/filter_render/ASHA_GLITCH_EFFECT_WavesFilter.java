package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_WavesFilter extends FilterRender {
    public static float f5385A;
    private static float f5386D;
    public static float f5387E;
    private static float f5388z;
    private String f5389B = "touchY";
    private int f5390C;
    private int f5391v;
    private int f5392w;
    private String f5393x = "touchX";
    private int f5394y;

    public ASHA_GLITCH_EFFECT_WavesFilter() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 1.0-mouse.x/iResolution.x*7.0;\nfloat amounty = 10.0+mouse.y/iResolution.y*60.0;\nvec2 uv = gl_FragCoord.xy; \nuv /= amounty;\nfloat y = floor(uv.y+.5);\n#define T texture2D(inputImageTexture,amounty*vec2(uv.x,y)/iResolution.xy)\ngl_FragColor += cos( 6.28*(uv.y-y) + amountx*(2.*T-1.) ) -gl_FragColor;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5394y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5393x);
        this.f5390C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5389B);
        this.f5391v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5392w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        try {
            f5385A = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
            f5387E = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
        } catch (Exception unused) {
            f5385A = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
            f5387E = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5394y, f5388z);
        GLES20.glUniform1f(this.f5390C, f5386D);
        try {
            GLES20.glUniform1f(this.f5391v, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
            GLES20.glUniform1f(this.f5392w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
        } catch (Exception unused) {
            GLES20.glUniform1f(this.f5391v, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
            GLES20.glUniform1f(this.f5392w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5388z = f5385A;
        f5386D = f5387E;
    }
}
