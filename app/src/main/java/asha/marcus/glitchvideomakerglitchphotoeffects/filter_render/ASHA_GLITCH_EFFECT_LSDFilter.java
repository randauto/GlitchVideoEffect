package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_LSDFilter extends FilterRender {
    public static float f5585A;
    private static float f5586D;
    public static float f5587E;
    private static float f5588z;
    private String f5589B = "touchY";
    private int f5590C;
    private int f5591v;
    private int f5592w;
    private String f5593x = "touchX";
    private int f5594y;

    public ASHA_GLITCH_EFFECT_LSDFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec4 t = texture2D(inputImageTexture, uv);\ngl_FragColor = smoothstep(vec4(0.0), vec4(0.9),fract(t*(1.6 * 1.6)* (amountx+0.05) * 4.));\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5594y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5593x);
        this.f5590C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5589B);
        this.f5591v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5592w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5585A = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5587E = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5585A = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5587E = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5585A = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5587E = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5585A = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5587E = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5594y, f5588z);
        GLES20.glUniform1f(this.f5590C, f5586D);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5591v, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5592w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5591v, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5592w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5591v, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5592w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5591v, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5592w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5588z = f5585A;
        f5586D = f5587E;
    }
}
