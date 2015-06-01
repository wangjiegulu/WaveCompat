package com.wangjie.wavecompat;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/1/15.
 */
public abstract class WaveDrawableBase extends Drawable{
    public interface OnWaveDrawableListener {
        void onWaveDrawableAnimatorStart();

        void onWaveDrawableAnimatorEnd();
    }

    public abstract void setOnWaveDrawableListener(OnWaveDrawableListener onWaveDrawableListener);

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
