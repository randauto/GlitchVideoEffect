package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_LinesFilter extends FilterRender {
    private static float f5605E = 0.0f;
    public static float f5606F = 0.0f;
    private static float f5607I = 0.0f;
    public static float f5608J = 0.0f;
    public static boolean f5609v = false;
    private float f5610A;
    private long f5611B = System.currentTimeMillis();
    private String f5612C = "touchX";
    private int f5613D;
    private String f5614G = "touchY";
    private int f5615H;
    private long f5616K;
    private int f5617w;
    private int f5618x;
    private String f5619y = "iTime";
    private int f5620z;

    public ASHA_GLITCH_EFFECT_LinesFilter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*0.05;\nvec4 fg = vec4(0.08, 0.07, 0.08, 1.0);\nvec4 bg = vec4(0.5, 0.14, 0.1, 1.0);\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat t1 = fract(amounty);\nif(sin(1.0 + t1 * 3.0 + tan(gl_FragCoord.y * (amounty*0.4*sin(iTime/5.))) - sqrt(gl_FragCoord.y * t1)) >= 0.7){\nfloat y = uv.y * 12.0;\nif(y - floor(y) >= 0.5){\nuv.x += amountx*0.1;\n}else{\nuv.x -= amountx*0.1;\n}\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv+vec2(amountx*0.04,0.0)).r;\ngl_FragColor.gb = texture2D(inputImageTexture, uv).gb;\n}");
        this.f5616K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5620z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5619y);
        this.f5613D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5612C);
        this.f5615H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5614G);
        this.f5617w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5618x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5606F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5608J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5606F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5608J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5606F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5608J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5606F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5608J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5620z, this.f5610A);
        GLES20.glUniform1f(this.f5613D, f5605E);
        GLES20.glUniform1f(this.f5615H, f5607I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5617w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5618x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5617w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5618x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5617w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5618x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5617w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5618x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5609v) {
            this.f5611B = System.currentTimeMillis() - this.f5616K;
            if (this.f5611B > 20000) {
                this.f5616K = System.currentTimeMillis();
            }
        }
        this.f5610A = (((float) this.f5611B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f5605E = f5606F;
        f5607I = f5608J;
    }
}
