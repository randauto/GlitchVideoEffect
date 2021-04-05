package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.content.Context;
import asha.marcus.glitchvideomakerglitchphotoeffects.C0476R;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_FilterHelper {
    public static int current_filter;
    public int[] Filters = {C0476R.C0477drawable.glitch_camera, C0476R.C0477drawable.glitch_gb, C0476R.C0477drawable.glitch_glitch2, C0476R.C0477drawable.glitch_lines, C0476R.C0477drawable.glitch_hotline, C0476R.C0477drawable.glitch_lens, C0476R.C0477drawable.glitch_rgbwave, C0476R.C0477drawable.glitch_rgb, C0476R.C0477drawable.glitch_rb, C0476R.C0477drawable.glitch_rg, C0476R.C0477drawable.glitch_plaza, C0476R.C0477drawable.glitch_colorize, C0476R.C0477drawable.glitch_shampain, C0476R.C0477drawable.glitch_emoji, C0476R.C0477drawable.glitch_bit16, C0476R.C0477drawable.glitch_clones, C0476R.C0477drawable.glitch_compression, C0476R.C0477drawable.glitch_data, C0476R.C0477drawable.glitch_fall, C0476R.C0477drawable.glitch_glitch, C0476R.C0477drawable.glitch_interference, C0476R.C0477drawable.glitch_lsd, C0476R.C0477drawable.glitch_magnitude, C0476R.C0477drawable.glitch_oldmobile, C0476R.C0477drawable.glitch_pixel, C0476R.C0477drawable.glitch_ripple, C0476R.C0477drawable.glitch_slicer, C0476R.C0477drawable.glitch_spectrum, C0476R.C0477drawable.glitch_stripes, C0476R.C0477drawable.glitch_vaporglitch, C0476R.C0477drawable.glitch_vhspause};
    private FilterRender mFilter1;
    private FilterRender mFilter10;
    private FilterRender mFilter11;
    private FilterRender mFilter12;
    private FilterRender mFilter13;
    private FilterRender mFilter14;
    private FilterRender mFilter15;
    private FilterRender mFilter16;
    private FilterRender mFilter17;
    private FilterRender mFilter18;
    private FilterRender mFilter19;
    private FilterRender mFilter2;
    private FilterRender mFilter20;
    private FilterRender mFilter21;
    private FilterRender mFilter22;
    private FilterRender mFilter23;
    private FilterRender mFilter24;
    private FilterRender mFilter25;
    private FilterRender mFilter26;
    private FilterRender mFilter27;
    private FilterRender mFilter28;
    private FilterRender mFilter29;
    private FilterRender mFilter3;
    private FilterRender mFilter30;
    private FilterRender mFilter31;
    private FilterRender mFilter4;
    private FilterRender mFilter5;
    private FilterRender mFilter6;
    private FilterRender mFilter7;
    private FilterRender mFilter8;
    private FilterRender mFilter9;

    public ASHA_GLITCH_EFFECT_FilterHelper(Context context, boolean z) {
        if (z) {
            this.mFilter1 = new ASHA_GLITCH_EFFECT_CameraFilter();
            this.mFilter2 = new ASHA_GLITCH_EFFECT_GBFilter();
            this.mFilter3 = new ASHA_GLITCH_EFFECT_Glitch2Filter();
            this.mFilter4 = new ASHA_GLITCH_EFFECT_LinesFilter();
            this.mFilter5 = new ASHA_GLITCH_EFFECT_HotlineFilter();
            this.mFilter6 = new ASHA_GLITCH_EFFECT_LensFilter();
            this.mFilter7 = new ASHA_GLITCH_EFFECT_RGBWaveFilter();
            this.mFilter8 = new ASHA_GLITCH_EFFECT_RGBFilter();
            this.mFilter9 = new ASHA_GLITCH_EFFECT_RBFilter();
            this.mFilter10 = new ASHA_GLITCH_EFFECT_RGFilter();
            this.mFilter11 = new ASHA_GLITCH_EFFECT_PlazaFilter();
            this.mFilter12 = new ASHA_GLITCH_EFFECT_ColorizeFilter();
            this.mFilter13 = new ASHA_GLITCH_EFFECT_ShampainFilter();
            this.mFilter14 = new ASHA_GLITCH_EFFECT_EmojiFilter(context);
            this.mFilter15 = new ASHA_GLITCH_EFFECT_Bit16Filter();
            this.mFilter16 = new ASHA_GLITCH_EFFECT_ClonesFilter();
            this.mFilter17 = new ASHA_GLITCH_EFFECT_CompressionFilter();
            this.mFilter18 = new ASHA_GLITCH_EFFECT_DataFilter();
            this.mFilter19 = new ASHA_GLITCH_EFFECT_FallFilter();
            this.mFilter20 = new ASHA_GLITCH_EFFECT_GlitchFilter();
            this.mFilter21 = new ASHA_GLITCH_EFFECT_InterferenceFilter();
            this.mFilter22 = new ASHA_GLITCH_EFFECT_LSDFilter();
            this.mFilter23 = new ASHA_GLITCH_EFFECT_MagnitudeFilter();
            this.mFilter24 = new ASHA_GLITCH_EFFECT_OldMobileFilter();
            this.mFilter25 = new ASHA_GLITCH_EFFECT_PixelFilter();
            this.mFilter26 = new ASHA_GLITCH_EFFECT_RippleFilter();
            this.mFilter27 = new ASHA_GLITCH_EFFECT_SlicerFilter();
            this.mFilter28 = new ASHA_GLITCH_EFFECT_SpectrumFilter();
            this.mFilter29 = new ASHA_GLITCH_EFFECT_StripesFilter();
            this.mFilter30 = new ASHA_GLITCH_EFFECT_VaporGlitchFilter();
            this.mFilter31 = new ASHA_GLITCH_EFFECT_VHSPauseFilter();
        }
    }

    public FilterRender getSelectedFilter(int i) {
        int i2 = i + 1;
        current_filter = i2;
        switch (i2) {
            case 1:
                return this.mFilter1;
            case 2:
                return this.mFilter2;
            case 3:
                return this.mFilter3;
            case 4:
                return this.mFilter4;
            case 5:
                return this.mFilter5;
            case 6:
                return this.mFilter6;
            case 7:
                return this.mFilter7;
            case 8:
                return this.mFilter8;
            case 9:
                return this.mFilter9;
            case 10:
                return this.mFilter10;
            case 11:
                return this.mFilter11;
            case 12:
                return this.mFilter12;
            case 13:
                return this.mFilter13;
            case 14:
                return this.mFilter14;
            case 15:
                return this.mFilter15;
            case 16:
                return this.mFilter16;
            case 17:
                return this.mFilter17;
            case 18:
                return this.mFilter18;
            case 19:
                return this.mFilter19;
            case 20:
                return this.mFilter20;
            case 21:
                return this.mFilter21;
            case 22:
                return this.mFilter22;
            case 23:
                return this.mFilter23;
            case 24:
                return this.mFilter24;
            case 25:
                return this.mFilter25;
            case 26:
                return this.mFilter26;
            case 27:
                return this.mFilter27;
            case 28:
                return this.mFilter28;
            case 29:
                return this.mFilter29;
            case 30:
                return this.mFilter30;
            case 31:
                return this.mFilter31;
            default:
                return this.mFilter1;
        }
    }

    public void setTouch(float f, float f2) {
        switch (current_filter) {
            case 2:
                ASHA_GLITCH_EFFECT_GBFilter.f5511A = f;
                ASHA_GLITCH_EFFECT_GBFilter.f5513E = f2;
                return;
            case 3:
                ASHA_GLITCH_EFFECT_Glitch2Filter.f5538F = f;
                ASHA_GLITCH_EFFECT_Glitch2Filter.f5540J = f2;
                return;
            case 4:
                ASHA_GLITCH_EFFECT_LinesFilter.f5606F = f;
                ASHA_GLITCH_EFFECT_LinesFilter.f5608J = f2;
                return;
            case 5:
                ASHA_GLITCH_EFFECT_HotlineFilter.f5554F = f;
                ASHA_GLITCH_EFFECT_HotlineFilter.f5556J = f2;
                return;
            case 6:
                ASHA_GLITCH_EFFECT_LensFilter.f5595A = f;
                ASHA_GLITCH_EFFECT_LensFilter.f5597E = f2;
                return;
            case 7:
                ASHA_GLITCH_EFFECT_RGBWaveFilter.f5255A = f;
                ASHA_GLITCH_EFFECT_RGBWaveFilter.f5257E = f2;
                return;
            case 8:
                ASHA_GLITCH_EFFECT_RGBFilter.f5699A = f;
                ASHA_GLITCH_EFFECT_RGBFilter.f5701E = f2;
                return;
            case 9:
                ASHA_GLITCH_EFFECT_RBFilter.f5679A = f;
                ASHA_GLITCH_EFFECT_RBFilter.f5681E = f2;
                return;
            case 10:
                ASHA_GLITCH_EFFECT_RGFilter.f5689A = f;
                ASHA_GLITCH_EFFECT_RGFilter.f5691E = f2;
                return;
            case 11:
                ASHA_GLITCH_EFFECT_PlazaFilter.f5664F = f;
                ASHA_GLITCH_EFFECT_PlazaFilter.f5666J = f2;
                return;
            case 12:
                ASHA_GLITCH_EFFECT_ColorizeFilter.f5444F = f;
                ASHA_GLITCH_EFFECT_ColorizeFilter.f5446J = f2;
                return;
            case 13:
                ASHA_GLITCH_EFFECT_ShampainFilter.f5282F = f;
                ASHA_GLITCH_EFFECT_ShampainFilter.f5284J = f2;
                return;
            case 14:
                ASHA_GLITCH_EFFECT_EmojiFilter.f5754I = f;
                ASHA_GLITCH_EFFECT_EmojiFilter.f5756M = f2;
                return;
            case 15:
                ASHA_GLITCH_EFFECT_Bit16Filter.f5417A = f;
                ASHA_GLITCH_EFFECT_Bit16Filter.f5419E = f2;
                return;
            case 16:
                ASHA_GLITCH_EFFECT_ClonesFilter.f5428F = f;
                ASHA_GLITCH_EFFECT_ClonesFilter.f5430J = f2;
                return;
            case 17:
                ASHA_GLITCH_EFFECT_CompressionFilter.f5460F = f;
                ASHA_GLITCH_EFFECT_CompressionFilter.f5462J = f2;
                return;
            case 18:
                ASHA_GLITCH_EFFECT_DataFilter.f5476F = f;
                ASHA_GLITCH_EFFECT_DataFilter.f5478J = f2;
                return;
            case 19:
                ASHA_GLITCH_EFFECT_FallFilter.f5501A = f;
                ASHA_GLITCH_EFFECT_FallFilter.f5503E = f2;
                return;
            case 20:
                ASHA_GLITCH_EFFECT_GlitchFilter.f5522F = f;
                ASHA_GLITCH_EFFECT_GlitchFilter.f5524J = f2;
                return;
            case 21:
                ASHA_GLITCH_EFFECT_InterferenceFilter.f5570F = f;
                ASHA_GLITCH_EFFECT_InterferenceFilter.f5572J = f2;
                return;
            case 22:
                ASHA_GLITCH_EFFECT_LSDFilter.f5585A = f;
                ASHA_GLITCH_EFFECT_LSDFilter.f5587E = f2;
                return;
            case 23:
                ASHA_GLITCH_EFFECT_MagnitudeFilter.f5622F = f;
                ASHA_GLITCH_EFFECT_MagnitudeFilter.f5624J = f2;
                return;
            case 24:
                ASHA_GLITCH_EFFECT_OldMobileFilter.f5638F = f;
                ASHA_GLITCH_EFFECT_OldMobileFilter.f5640J = f2;
                return;
            case 25:
                ASHA_GLITCH_EFFECT_PixelFilter.f5653A = f;
                ASHA_GLITCH_EFFECT_PixelFilter.f5655E = f2;
                return;
            case 26:
                ASHA_GLITCH_EFFECT_RippleFilter.f5266F = f;
                ASHA_GLITCH_EFFECT_RippleFilter.f5268J = f2;
                return;
            case 27:
                ASHA_GLITCH_EFFECT_SlicerFilter.f5314F = f;
                ASHA_GLITCH_EFFECT_SlicerFilter.f5316J = f2;
                return;
            case 28:
                ASHA_GLITCH_EFFECT_SpectrumFilter.f5330F = f;
                ASHA_GLITCH_EFFECT_SpectrumFilter.f5332J = f2;
                return;
            case 30:
                ASHA_GLITCH_EFFECT_VaporGlitchFilter.f5370F = f;
                ASHA_GLITCH_EFFECT_VaporGlitchFilter.f5372J = f2;
                return;
            case 31:
                ASHA_GLITCH_EFFECT_VHSPauseFilter.f5354F = f;
                ASHA_GLITCH_EFFECT_VHSPauseFilter.f5356J = f2;
                return;
            default:
                return;
        }
    }
}
