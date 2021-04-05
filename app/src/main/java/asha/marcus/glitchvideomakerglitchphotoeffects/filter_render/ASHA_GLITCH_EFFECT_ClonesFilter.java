package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_ClonesFilter extends FilterRender {
    private static float f5427E = 0.0f;
    public static float f5428F = 0.0f;
    private static float f5429I = 0.0f;
    public static float f5430J = 0.0f;
    public static boolean f5431v = false;
    private float f5432A;
    private long f5433B = System.currentTimeMillis();
    private String f5434C = "touchX";
    private int f5435D;
    private String f5436G = "touchY";
    private int f5437H;
    private long f5438K;
    private int f5439w;
    private int f5440x;
    private String f5441y = "iTime";
    private int f5442z;

    public ASHA_GLITCH_EFFECT_ClonesFilter() {
        setFragmentShader("precision lowp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define taps 6.0\n#define tau 6.28\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = textureCoordinate.xy;\nvec4 c = texture2D(inputImageTexture,uv);\nfloat t = iTime*0.5;\nfloat d = amountx/2.;\nfor(float i = 0.; i<tau;i+=tau/taps){\nfloat a = i+t;\nvec4 c2 = texture2D(inputImageTexture,vec2(uv.x+cos(a)*d,uv.y+sin(a)*d));\n#ifdef light\nc = max(c,c2);\n#else\nc = min(c,c2);\n#endif\n}\ngl_FragColor = c;\n}");
        this.f5438K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5442z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5441y);
        this.f5435D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5434C);
        this.f5437H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5436G);
        this.f5439w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5440x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5428F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5430J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5428F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5430J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5428F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5430J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5428F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5430J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5442z, this.f5432A);
        GLES20.glUniform1f(this.f5435D, f5427E);
        GLES20.glUniform1f(this.f5437H, f5429I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5439w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5440x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5439w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5440x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5439w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5440x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5439w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5440x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5431v) {
            this.f5433B = System.currentTimeMillis() - this.f5438K;
            if (this.f5433B > 50000) {
                this.f5438K = System.currentTimeMillis();
            }
        }
        this.f5432A = (((float) this.f5433B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f5427E = f5428F;
        f5429I = f5430J;
    }
}
