package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import asha.marcus.glitchvideomakerglitchphotoeffects.C0476R;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import p004cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class ASHA_GLITCH_EFFECT_EmojiFilter extends MultiBitmapInputRender {
    private static float f5753H;
    public static float f5754I;
    private static float f5755L;
    public static float f5756M;
    private int f5757C;
    private int f5758D;
    private String f5759F = "touchX";
    private int f5760G;
    private String f5761J = "touchY";
    private int f5762K;

    public ASHA_GLITCH_EFFECT_EmojiFilter(Context context) {
        super(context, getBitmaps(context));
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define G 11 \nvoid main() {  \nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 fragCoord = vec2(gl_FragCoord.x, gl_FragCoord.y);\nvec2 fragColor = vec2(gl_FragColor.x, gl_FragColor.y);\nvec2 R = iResolution.xy;\nvec2 S = vec2(amountx*1.6,amountx*1.6);\nfragCoord /= S;\nfragColor-=fragColor;\nfloat v = texture2D(inputImageTexture,floor(fragCoord)*S/R).r;\nfragCoord = fract(fragCoord);\nfragCoord.x = fragCoord.x/1.0;\nint c = int(float(G)*v);\nfloat mix1 = 1.0 - amountx/10.;\nif (mix1 < 0.0){\nmix1 = 0.0;\n}\ngl_FragColor.rgb = mix(texture2D(inputImageTexture2, fragCoord/5. + fract( vec2(c, c/5) / 5.)), texture2D(inputImageTexture, gl_FragCoord.xy/R), mix1).rgb;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5760G = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5759F);
        this.f5762K = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5761J);
        this.f5757C = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5758D = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5754I = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5756M = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5754I = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5756M = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5754I = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5756M = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5754I = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5756M = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5760G, f5753H);
        GLES20.glUniform1f(this.f5762K, f5755L);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5757C, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5758D, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5757C, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5758D, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5757C, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5758D, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5757C, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5758D, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5753H = f5754I;
        f5755L = f5756M;
    }

    public static String[] getBitmaps(Context context) {
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), C0476R.C0477drawable.emojigrid);
        File cacheDir = context.getCacheDir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(cacheDir, "Emoji.jpg"));
            decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        String str = cacheDir.getAbsolutePath().toString() + "/" + "Emoji.jpg";
        if (!new File(str).exists()) {
            Log.e("file", "no image file at location :" + str);
        }
        return new String[]{str};
    }
}
