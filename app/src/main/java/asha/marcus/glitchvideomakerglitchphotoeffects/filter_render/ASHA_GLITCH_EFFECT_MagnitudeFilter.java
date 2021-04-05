package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_MagnitudeFilter extends FilterRender {
    private static float f5621E = 0.0f;
    public static float f5622F = 0.0f;
    private static float f5623I = 0.0f;
    public static float f5624J = 0.0f;
    public static boolean f5625v = false;
    private float f5626A;
    private long f5627B = System.currentTimeMillis();
    private String f5628C = "touchX";
    private int f5629D;
    private String f5630G = "touchY";
    private int f5631H;
    private long f5632K;
    private int f5633w;
    private int f5634x;
    private String f5635y = "iTime";
    private int f5636z;

    public ASHA_GLITCH_EFFECT_MagnitudeFilter() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nuniform float iTime;\nfloat rand(vec2 co){\nfloat b = 10.;\nfloat dt= dot(co.xy ,vec2(b,b));\nfloat sn= mod(dt,0.);\nreturn fract(tan(sn) * b);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat magnitude = 0.1 * amountx;\nvec2 offsetRedUV = uv;\noffsetRedUV.x = uv.x + rand(vec2(iTime*0.03,uv.y*2.42)) * 0.02 * amountx;\noffsetRedUV.x += cos(rand(vec2(iTime*0.2, uv.y)))*magnitude*uv.x;\nvec2 offsetGreenUV = uv;\noffsetGreenUV.x = uv.x + rand(vec2(iTime*0.4,uv.y*uv.x*100000.0)) * .2 * amountx;\noffsetGreenUV.x += cos(iTime*9.0)*magnitude*uv.y;\nvec2 offsetBlueUV = uv;\noffsetBlueUV.x = uv.x + rand(vec2(iTime/0.4,uv.y*uv.x*100000.0)) * .2 * amountx;\noffsetBlueUV.x += rand(vec2(cos(iTime*0.1),sin(uv.x*uv.y)));\nfloat r = texture2D(inputImageTexture, offsetRedUV).r;\nfloat g = texture2D(inputImageTexture, offsetGreenUV).g;\nfloat b = texture2D(inputImageTexture, uv).b;\ngl_FragColor = vec4(r,g,b,0);\n}");
        this.f5632K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5636z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5635y);
        this.f5629D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5628C);
        this.f5631H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5630G);
        this.f5633w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5634x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5622F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5624J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5622F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5624J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5622F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5624J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5622F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5624J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5636z, this.f5626A);
        GLES20.glUniform1f(this.f5629D, f5621E);
        GLES20.glUniform1f(this.f5631H, f5623I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5633w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5634x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5633w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5634x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5633w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5634x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5633w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5634x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5625v) {
            this.f5627B = System.currentTimeMillis() - this.f5632K;
            if (this.f5627B > 20000) {
                this.f5632K = System.currentTimeMillis();
            }
        }
        this.f5626A = (((float) this.f5627B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f5621E = f5622F;
        f5623I = f5624J;
    }
}
