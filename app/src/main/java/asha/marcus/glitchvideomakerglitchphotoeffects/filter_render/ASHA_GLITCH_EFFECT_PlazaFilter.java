package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_PlazaFilter extends FilterRender {
    private static float f5663E = 0.0f;
    public static float f5664F = 0.0f;
    private static float f5665I = 0.0f;
    public static float f5666J = 0.0f;
    public static boolean f5667v = false;
    private float f5668A;
    private long f5669B = System.currentTimeMillis();
    private String f5670C = "touchX";
    private int f5671D;
    private String f5672G = "touchY";
    private int f5673H;
    private long f5674K;
    private int f5675w;
    private int f5676x;
    private String f5677y = "iTime";
    private int f5678z;

    public ASHA_GLITCH_EFFECT_PlazaFilter() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rng2(vec2 seed){\nreturn fract(sin(dot(seed * floor(iTime * 60.), vec2(127.1,311.7))) * 43758.5453123);\n}\nfloat rng(float seed){\nreturn rng2(vec2(seed, 1.0));\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.;\namounty = 1.0-amounty;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 blockS = floor(uv * vec2(iResolution.x/70., iResolution.y/70.));\nvec2 blockL = floor(uv * vec2(iResolution.x/70., iResolution.y/70.));\nfloat lineNoise = pow(rng2(blockS), 1.0) * pow(rng(7.2341), 1.0);\nif( uv.x > amountx-0.3*rng(10.) && uv.x < amountx+0.3*rng(11.)){\nif( uv.y > amounty-0.3*rng(13.) && uv.y < amounty+0.3*rng(12.)){  \nvec4 col1 = texture2D(inputImageTexture, uv);\n vec4 col2 = texture2D(inputImageTexture, uv + vec2(lineNoise * 0.25 * rng(5.0), 0));\nvec4 col3 = texture2D(inputImageTexture, uv - vec2(lineNoise * 0.25 * rng(31.0), 0));\ngl_FragColor = vec4(vec3(col1.x, col2.y, col3.z), 1.0);\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}");
        this.f5674K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5678z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5677y);
        this.f5671D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5670C);
        this.f5673H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5672G);
        this.f5675w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5676x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5664F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5666J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5664F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5666J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5664F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5666J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5664F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5666J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5678z, this.f5668A);
        GLES20.glUniform1f(this.f5671D, f5663E);
        GLES20.glUniform1f(this.f5673H, f5665I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5675w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5676x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5675w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5676x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5675w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5676x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5675w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5676x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5667v) {
            this.f5669B = System.currentTimeMillis() - this.f5674K;
            if (this.f5669B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5674K = System.currentTimeMillis();
            }
        }
        this.f5668A = (((float) this.f5669B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5663E = f5664F;
        f5665I = f5666J;
    }
}
