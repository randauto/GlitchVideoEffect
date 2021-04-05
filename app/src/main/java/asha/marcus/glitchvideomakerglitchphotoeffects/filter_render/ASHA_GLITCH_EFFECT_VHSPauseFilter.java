package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_VHSPauseFilter extends FilterRender {
    private static float f5353E = 0.0f;
    public static float f5354F = 0.0f;
    private static float f5355I = 0.0f;
    public static float f5356J = 0.0f;
    public static boolean f5357v = false;
    private float f5358A;
    private long f5359B = System.currentTimeMillis();
    private String f5360C = "touchX";
    private int f5361D;
    private String f5362G = "touchY";
    private int f5363H;
    private long f5364K;
    private int f5365w;
    private int f5366x;
    private String f5367y = "iTime";
    private int f5368z;

    public ASHA_GLITCH_EFFECT_VHSPauseFilter() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\n}\nvoid main(){\nvec4 texColor = vec4(0);\nvec2 samplePosition = gl_FragCoord.xy / iResolution.xy;\nif(touchY > 0.0){\nfloat whiteNoise = 9999.0;\nsamplePosition.x = samplePosition.x+(rand(vec2(iTime,gl_FragCoord.y))-0.5)/64.0;\nsamplePosition.y = samplePosition.y+(rand(vec2(iTime))-0.5)/32.0;\ntexColor = texColor + (vec4(-0.5)+vec4(rand(vec2(gl_FragCoord.y,iTime)),rand(vec2(gl_FragCoord.y,iTime+1.0)),rand(vec2(gl_FragCoord.y,iTime+2.0)),0))*0.1;\nwhiteNoise = rand(vec2(floor(samplePosition.y*80.0),floor(samplePosition.x*50.0))+vec2(iTime,0));\nif (whiteNoise > 11.5-30.0*samplePosition.y || whiteNoise < 1.5-5.0*samplePosition.y) {\ntexColor = texColor + texture2D(inputImageTexture,samplePosition);\n} else {\ntexColor = vec4(1);\n}\n}else{\ntexColor = texture2D(inputImageTexture, samplePosition);\n}\ngl_FragColor = texColor;\n}");
        this.f5364K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5368z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5367y);
        this.f5361D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5360C);
        this.f5363H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5362G);
        this.f5365w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5366x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5368z, this.f5358A);
        GLES20.glUniform1f(this.f5361D, f5353E);
        GLES20.glUniform1f(this.f5363H, f5355I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5365w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5366x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5365w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5366x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5365w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5366x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5365w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5366x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5357v) {
            this.f5359B = System.currentTimeMillis() - this.f5364K;
            if (this.f5359B > 1000) {
                this.f5364K = System.currentTimeMillis();
            }
        }
        this.f5358A = (((float) this.f5359B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f5353E = f5354F;
        f5355I = f5356J;
    }
}
