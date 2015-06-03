package com.wangjie.wavecompat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/27/15.
 */
public abstract class WaveContainer extends PopupWindow implements WaveDrawable.OnWaveDrawableListener {

    private static final String TAG = WaveContainer.class.getSimpleName();
    private WaveDrawableBase waveDrawable;

    public WaveContainer(View view, WaveDrawableBase waveDrawable, int width, int height) {
        super(view, width, height, false);
        this.waveDrawable = waveDrawable;
        initWaveDrawable(view);
    }

    private void initWaveDrawable(View view) {
//        WaveDrawable waveDrawable = new WaveDrawable(view);
//        waveDrawable.setFrameBitmap(frameBitmap);
        waveDrawable.setOnWaveDrawableListener(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(waveDrawable);
        } else {
            view.setBackground(waveDrawable);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }


    @Override
    public void onWaveDrawableAnimatorStart() {

    }

    @Override
    public void onWaveDrawableAnimatorEnd() {
        if (!this.isShowing()) {
            return;
        }
        View view = getContentView();
        Context context = view.getContext();
        if (context instanceof Activity) {
            Activity activity = ((Activity) context);
            if (activity.isFinishing()) {
                dismiss();
                return;
            }
        }
        onWaveDrawableAnimatorEndExtraAction();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    WaveContainer.this.dismiss();
                } catch (Exception ex) {
                    Log.w(TAG, ex);
                }
            }
        }, 500);
    }

    protected abstract void onWaveDrawableAnimatorEndExtraAction();
}
