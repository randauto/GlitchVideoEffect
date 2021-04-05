package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_HotlineFilter extends FilterRender {
    private static float f5553E = 0.0f;
    public static float f5554F = 0.0f;
    private static float f5555I = 0.0f;
    public static float f5556J = 0.0f;
    public static boolean f5557v = false;
    private float f5558A;
    private long f5559B = System.currentTimeMillis();
    private String f5560C = "touchX";
    private int f5561D;
    private String f5562G = "touchY";
    private int f5563H;
    private long f5564K;
    private int f5565w;
    private int f5566x;
    private String f5567y = "iTime";
    private int f5568z;

    public ASHA_GLITCH_EFFECT_HotlineFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 size = vec2(50.0, 50.0);\nvec2 distortion = vec2((30.0 * amountx), (30.0 * amountx));\nfloat speed = .75;\nvec2 transformed = vec2(\ngl_FragCoord.x + sin(gl_FragCoord.y / size.x + iTime * speed) * distortion.x,\ngl_FragCoord.y + cos(gl_FragCoord.x / size.y + iTime * speed) * distortion.y\n);\nvec2 relCoord = gl_FragCoord.xy / iResolution.xy;\ngl_FragColor = texture2D(inputImageTexture, transformed / iResolution.xy) + vec4(\n(cos(relCoord.x + iTime * speed * 4.0) + 1.0) / 2.0,\n(relCoord.x + relCoord.y) / 2.0,\n(sin(relCoord.y + iTime * speed) + 1.0) / 2.0, 0 ) / (1. / amountx);\n}\n");
        this.f5564K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5568z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5567y);
        this.f5561D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5560C);
        this.f5563H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5562G);
        this.f5565w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5566x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5554F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5556J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5554F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5556J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5554F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5556J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5554F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5556J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5568z, this.f5558A);
        GLES20.glUniform1f(this.f5561D, f5553E);
        GLES20.glUniform1f(this.f5563H, f5555I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5565w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5566x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5565w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5566x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5565w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5566x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5565w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5566x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5557v) {
            this.f5559B = System.currentTimeMillis() - this.f5564K;
            if (this.f5559B > 50000) {
                this.f5564K = System.currentTimeMillis();
            }
        }
        this.f5558A = (((float) this.f5559B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f5553E = f5554F;
        f5555I = f5556J;
    }
}
