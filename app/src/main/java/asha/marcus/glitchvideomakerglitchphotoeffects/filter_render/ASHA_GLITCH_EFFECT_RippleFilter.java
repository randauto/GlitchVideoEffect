package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_RippleFilter extends FilterRender {
    private static float f5265E = 0.0f;
    public static float f5266F = 0.0f;
    private static float f5267I = 0.0f;
    public static float f5268J = 0.0f;
    public static boolean f5269v = false;
    private float f5270A;
    private long f5271B = System.currentTimeMillis();
    private String f5272C = "touchX";
    private int f5273D;
    private String f5274G = "touchY";
    private int f5275H;
    private long f5276K;
    private int f5277w;
    private int f5278x;
    private String f5279y = "iTime";
    private int f5280z;

    public ASHA_GLITCH_EFFECT_RippleFilter() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat x(float t) {\nt = mod(t, 1.0);\nreturn abs(t) - abs(t-1.0) - abs(t-2.0) + abs(t-3.0) - 1.0;\t\n}\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 1.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*12.0;\nfloat amounty = 0.1+mouse.y/iResolution.y*2.0;\namounty = 2.-amounty;\nfloat time = floor(iTime*(1.0*60.0));\nfloat rnd = random2d(vec2(time , 1.0));\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 p = abs(mod(uv*90.0, 1.0));\nvec2 cell = floor(uv*100.0);\nfloat t =  atan(cell.y+rnd*100.,cell.x)/1.57*4.0+ length(texture2D(inputImageTexture,uv));\nt *= 2.5;\nvec2 s = vec2(x(t), x(t-1.0))*(0.05*100.)+0.5; \nfloat d = max(abs(p.x-s.x), abs(p.y-s.y))* amounty;\ngl_FragColor = vec4(smoothstep(0.35, 0.051, d))* texture2D(inputImageTexture,cell / 100.0).bbra * texture2D(inputImageTexture,cell / 100.0).rbra * 3.2 ;\ngl_FragColor = mix(gl_FragColor, texture2D(inputImageTexture, uv), 1.0 - amountx/10.);\n}");
        this.f5276K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5280z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5279y);
        this.f5273D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5272C);
        this.f5275H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5274G);
        this.f5277w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5278x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5266F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5268J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5266F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5268J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5266F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5268J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5266F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5268J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5280z, this.f5270A);
        GLES20.glUniform1f(this.f5273D, f5265E);
        GLES20.glUniform1f(this.f5275H, f5267I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5277w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5278x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5277w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5278x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5277w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5278x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5277w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5278x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5269v) {
            this.f5271B = System.currentTimeMillis() - this.f5276K;
            if (this.f5271B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5276K = System.currentTimeMillis();
            }
        }
        this.f5270A = (((float) this.f5271B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5265E = f5266F;
        f5267I = f5268J;
    }
}
