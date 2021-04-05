package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;

import cn.ezandroid.ezfilter.core.FilterRender;
import cn.ezandroid.ezfilter.demo.Camera2FilterActivity;

public class Glitch2Filter extends FilterRender {
    private static float glitchWidth = 0.0f;
    public static float mWidth = 0.0f;
    private static float glitchHeight = 0.0f;
    public static float mHeight = 0.0f;
    public static boolean f5541v = false;
    private float f5542A;
    private long mTime = System.currentTimeMillis();
    private String f5544C = "touchX";
    private int f5545D;
    private String f5546G = "touchY";
    private int f5547H;
    private long mCurrentTime;
    private int f5549w;
    private int f5550x;
    private String f5551y = "iTime";
    private int f5552z;

    public Glitch2Filter() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 0.7);\n}\nfloat randomRange (in vec2 seed, in float min, in float max) {\nreturn min + random2d(seed) * (max - min);\n}\nfloat insideRange(float v, float bottom, float top) {\nreturn step(bottom, v) - step(top, v);\n}\nfloat SPEED = 0.6;\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat time = floor(iTime * SPEED * 60.0);    \nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 outCol = texture2D(inputImageTexture, uv).rgb;\nfloat rnd = random2d(vec2(time , 9545.0));\nvec2 colOffset = vec2(randomRange(vec2(time , 9545.0), 0.0, 0.1), \nrandomRange(vec2(time , 7205.0), 0.0, 0.1));\nif (rnd < 0.33){\noutCol.g = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).g;\n}else if (rnd < 0.66){\noutCol.r = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).r;\n}else{\noutCol.b = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).b;\n}\ngl_FragColor = vec4(outCol,1.0);\n}");
        this.mCurrentTime = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5552z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5551y);
        this.f5545D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5544C);
        this.f5547H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5546G);
        this.f5549w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5550x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                mWidth = (float) (Camera2FilterActivity.mRenderView.getWidth() / 2);
                mHeight = (float) (Camera2FilterActivity.mRenderView.getHeight() / 2);
            } catch (Exception unused) {
                mWidth = (float) (Camera2FilterActivity.mRenderView.getWidth() / 2);
                mHeight = (float) (Camera2FilterActivity.mRenderView.getHeight() / 2);
            }
        } else {
            try {
                mWidth = (float) (Camera2FilterActivity.mRenderView.getWidth() / 2);
                mHeight = (float) (Camera2FilterActivity.mRenderView.getHeight() / 2);
            } catch (Exception unused2) {
                mWidth = (float) (Camera2FilterActivity.mRenderView.getWidth() / 2);
                mHeight = (float) (Camera2FilterActivity.mRenderView.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5552z, this.f5542A);
        GLES20.glUniform1f(this.f5545D, glitchWidth);
        GLES20.glUniform1f(this.f5547H, glitchHeight);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5549w, (float) Camera2FilterActivity.mRenderView.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) Camera2FilterActivity.mRenderView.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5549w, (float) Camera2FilterActivity.mRenderView.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) Camera2FilterActivity.mRenderView.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5549w, (float) Camera2FilterActivity.mRenderView.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) Camera2FilterActivity.mRenderView.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5549w, (float) Camera2FilterActivity.mRenderView.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) Camera2FilterActivity.mRenderView.getHeight());
            }
        }
    }

    @Override
    protected void onDraw() {
        super.onDraw();
        if (!f5541v) {
            this.mTime = System.currentTimeMillis() - this.mCurrentTime;
            if (this.mTime > 2000) {
                this.mCurrentTime = System.currentTimeMillis();
            }
        }
        this.f5542A = (((float) this.mTime) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        glitchWidth = mWidth;
        glitchHeight = mHeight;
    }
}
