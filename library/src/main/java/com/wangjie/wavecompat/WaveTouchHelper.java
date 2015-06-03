package com.wangjie.wavecompat;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
public class WaveTouchHelper implements View.OnTouchListener {
    public interface OnWaveTouchHelperListener {
        void onWaveTouchUp(View view, Point locationInView, Point locationInScreen);
    }

    private View view;
    private OnWaveTouchHelperListener onWaveTouchHelperListener;

    public static WaveTouchHelper bindWaveTouchHelper(@NonNull View view, @NonNull OnWaveTouchHelperListener onWaveTouchHelperListener){
        return new WaveTouchHelper(view, onWaveTouchHelperListener);
    }
    public WaveTouchHelper(@NonNull View view, @NonNull OnWaveTouchHelperListener onWaveTouchHelperListener) {
        this.view = view;
        this.onWaveTouchHelperListener = onWaveTouchHelperListener;
        this.view.setOnTouchListener(this);
        this.view.setClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x < 0 || x > v.getWidth() || y < 0 || y > v.getHeight()) {
                    return false;
                } else {
                    if (null != onWaveTouchHelperListener) {
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        onWaveTouchHelperListener.onWaveTouchUp(view, new Point(x, y), new Point(location[0] + x, location[1] + y));
                    }
                }
                break;
        }
        return false;
    }

}
