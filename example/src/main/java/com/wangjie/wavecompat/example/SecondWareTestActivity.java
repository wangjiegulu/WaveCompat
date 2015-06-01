package com.wangjie.wavecompat.example;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.wavecompat.WaveCompat;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
@AILayout(R.layout.second_ware_test)
public class SecondWareTestActivity extends AIActionBarActivity {
    private int backgroundFromColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable((backgroundFromColor = getIntent().getIntExtra(WaveCompat.IntentKey.BACKGROUND_COLOR, 0xff8B7D6B))));

//        WaveCompat.transitionInitial(this, ABTextUtil.dip2px(context, 80), backgroundFromColor, Color.GRAY);

    }

    @Override
    public void onWindowInitialized() {
        super.onWindowInitialized();
        WaveCompat.transitionDefaultInitial(this, ABTextUtil.dip2px(context, 80), backgroundFromColor, Color.GRAY);
    }

}
