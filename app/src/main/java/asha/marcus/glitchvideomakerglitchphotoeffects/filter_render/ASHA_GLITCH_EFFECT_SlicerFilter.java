package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_SlicerFilter extends FilterRender {
    private static float f5313E = 0.0f;
    public static float f5314F = 0.0f;
    private static float f5315I = 0.0f;
    public static float f5316J = 0.0f;
    public static boolean f5317v = false;
    private float f5318A;
    private long f5319B = System.currentTimeMillis();
    private String f5320C = "touchX";
    private int f5321D;
    private String f5322G = "touchY";
    private int f5323H;
    private long f5324K;
    private int f5325w;
    private int f5326x;
    private String f5327y = "iTime";
    private int f5328z;

    public ASHA_GLITCH_EFFECT_SlicerFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy + iTime,vec2(12.0 ,78.0)))) * 1.0;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0-mouse.y/iResolution.y*60.;\nfloat ring1 = amounty;\nfloat ring2 = amounty/2.;\nfloat push1 = 5.4;\nfloat push2 = 10.0;\nfloat diminish = 0.05;\nfloat time = iTime ;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat r1 = rand(floor(uv.yy*ring1 )/ring1);\nfloat r2 = rand(floor(uv.yy*ring2 )/ring2);\nr1 = -1.0 + 2.0 * r1;\nr2 = -1.0 + 2.0 * r2;\nr1 *= (push1 * amountx);\nr2 *= (push2 * amountx);\nr1 += r2;\nr1 *= diminish;\nvec4 tex = texture2D(inputImageTexture, uv + vec2(r1,0.0));\nif(uv.x+r1 > (1.0 - amountx) || uv.x+r1 <= (amountx)){\ngl_FragColor = vec4(vec3(0.0),1.0);   \n} else {\ngl_FragColor =tex;\n}\n}");
        this.f5324K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5328z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5327y);
        this.f5321D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5320C);
        this.f5323H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5322G);
        this.f5325w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5326x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5314F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5316J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5314F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5316J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5314F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5316J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5314F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5316J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5328z, this.f5318A);
        GLES20.glUniform1f(this.f5321D, f5313E);
        GLES20.glUniform1f(this.f5323H, f5315I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5325w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5326x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5325w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5326x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5325w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5326x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5325w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5326x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5317v) {
            this.f5319B = System.currentTimeMillis() - this.f5324K;
            if (this.f5319B > 1000) {
                this.f5324K = System.currentTimeMillis();
            }
        }
        this.f5318A = (((float) this.f5319B) / 500.0f) * 2.0f * 3.14159f * 0.01f;
        f5313E = f5314F;
        f5315I = f5316J;
    }
}
