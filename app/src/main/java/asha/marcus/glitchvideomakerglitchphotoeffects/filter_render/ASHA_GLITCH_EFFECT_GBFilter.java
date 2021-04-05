package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_GBFilter extends FilterRender {
    public static float f5511A;
    private static float f5512D;
    public static float f5513E;
    private static float f5514z;
    private String f5515B = "touchY";
    private int f5516C;
    private int f5517v;
    private int f5518w;
    private String f5519x = "touchX";
    private int f5520y;

    public ASHA_GLITCH_EFFECT_GBFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 col;\ncol.r = texture2D( inputImageTexture, vec2((uv.x+amountx/10.)+0.05, (uv.y+amounty/10.)-0.05) ).r;\ncol.gb = texture2D( inputImageTexture, uv ).gb;\ngl_FragColor = vec4(col,1.0);\n}\n");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5520y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5519x);
        this.f5516C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5515B);
        this.f5517v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5518w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5511A = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5513E = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5511A = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5513E = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5511A = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5513E = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5511A = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5513E = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5520y, f5514z);
        GLES20.glUniform1f(this.f5516C, f5512D);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5517v, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5518w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5517v, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5518w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5517v, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5518w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5517v, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5518w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5514z = f5511A;
        f5512D = f5513E;
    }
}
