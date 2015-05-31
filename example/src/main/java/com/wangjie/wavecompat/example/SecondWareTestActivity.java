package com.wangjie.wavecompat.example;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
@AILayout(R.layout.second_ware_test)
public class SecondWareTestActivity extends AIActionBarActivity {

    @Override
    public void onWindowInitialized() {
        super.onWindowInitialized();
        getWindow().setBackgroundDrawable(new ColorDrawable(getIntent().getIntExtra("color", 0xff8B7D6B)));

        startDisplayAnimator();
    }

    private void startDisplayAnimator() {
        final View contentView = findViewById(android.R.id.content);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
        ValueAnimator marginTopAnimator = ValueAnimator.ofInt(ABTextUtil.dip2px(context, 80), 0);
        marginTopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) contentView.getLayoutParams();
                lp.topMargin = value;
                contentView.setLayoutParams(lp);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(marginTopAnimator).with(alphaAnimator);
        animatorSet.setDuration(200l);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();

    }

}
