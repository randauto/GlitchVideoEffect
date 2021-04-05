package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_Bit16Filter extends FilterRender {
    public static float f5417A;
    private static float f5418D;
    public static float f5419E;
    private static float f5420z;
    private String f5421B = "touchY";
    private int f5422C;
    private int f5423v;
    private int f5424w;
    private String f5425x = "touchX";
    private int f5426y;

    public ASHA_GLITCH_EFFECT_Bit16Filter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat hash(vec2 p) {\nreturn fract(1e4 * sin(17.0 * p.x + p.y * 0.1) * (0.1 + abs(sin(p.y * 13.0 + p.x))));\n}\nfloat compare(vec3 a, vec3 b) {\na = max(vec3(0.0), a - min(a.r, min(a.g, a.b)) * 0.25);\nb = max(vec3(0.0), b - min(b.r, min(b.g, b.b)) * 0.25);\na*=a*a;\nb*=b*b;\nvec3 diff = (a - b);\nreturn dot(diff, diff);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat pixelSize = amountx;\nvec2 c = floor(gl_FragCoord.xy / pixelSize);\nvec2 coord = c * pixelSize;\nvec3 src = texture2D(inputImageTexture, coord / iResolution.xy).rgb;\nvec3 dst0 = vec3(0), dst1 = vec3(0);\nfloat best0 = 1e3, best1 = 1e3;\n#define TRY(R, G, B) { const vec3 tst = vec3(R, G, B); float err = compare(src, tst); if (err < best0) { best1 = best0; dst1 = dst0; best0 = err; dst0 = tst; } }\nTRY(0.078431, 0.047059, 0.109804);\nTRY(0.266667, 0.141176, 0.203922);\nTRY(0.188235, 0.203922, 0.427451);\nTRY(0.305882, 0.290196, 0.305882);\nTRY(0.521569, 0.298039, 0.188235);\nTRY(0.203922, 0.396078, 0.141176);\nTRY(0.815686, 0.274510, 0.282353);\nTRY(0.458824, 0.443137, 0.380392);\nTRY(0.349020, 0.490196, 0.807843);\nTRY(0.823529, 0.490196, 0.172549);\nTRY(0.521569, 0.584314, 0.631373);\nTRY(0.427451, 0.666667, 0.172549);\nTRY(0.823529, 0.666667, 0.600000);\nTRY(0.427451, 0.760784, 0.792157);\nTRY(0.854902, 0.831373, 0.368627);\nTRY(0.870588, 0.933333, 0.839216);\n#undef TRY\nbest0 = sqrt(best0); best1 = sqrt(best1);\ngl_FragColor = vec4(mod(c.x + c.y, 2.0) >  (hash(c * 2.0) * 0.75) + (best1 / (best0 + best1)) ? dst1 : dst0, 1.0);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5426y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5425x);
        this.f5422C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5421B);
        this.f5423v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5424w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5417A = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5419E = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5417A = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5419E = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5417A = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5419E = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5417A = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5419E = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5426y, f5420z);
        GLES20.glUniform1f(this.f5422C, f5418D);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5423v, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5424w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5423v, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5424w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5423v, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5424w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5423v, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5424w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5420z = f5417A;
        f5418D = f5419E;
    }
}
