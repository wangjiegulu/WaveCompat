package com.wangjie.wavecompat.example;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.androidinject.annotation.annotations.base.AIClick;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.wavecompat.WaveCompat;
import com.wangjie.wavecompat.WaveDrawable;
import com.wangjie.wavecompat.WaveTouchHelper;

@AILayout(R.layout.activity_main)
public class MainActivity extends AIActionBarActivity implements WaveTouchHelper.OnWaveTouchHelperListener {
    @AIView(R.id.activity_main_a_tv)
    private TextView aTv;
    @AIView(R.id.activity_main_b_tv)
    private TextView bTv;
    @AIView(R.id.activity_main_c_tv)
    private TextView cTv;
    @AIView(R.id.activity_main_d_tv)
    private TextView dTv;
    @AIView(R.id.activity_main_e_tv)
    private TextView eTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaveTouchHelper.bindWaveTouchHelper(aTv, this);
        WaveTouchHelper.bindWaveTouchHelper(bTv, this);
        WaveTouchHelper.bindWaveTouchHelper(cTv, this);
        WaveTouchHelper.bindWaveTouchHelper(dTv, this);
        WaveTouchHelper.bindWaveTouchHelper(eTv, this);

        ABViewUtil.setBackgroundDrawable(eTv, ABShape.selectorClickColorCornerSimple(0xff218868, 0x88218868, 0));
    }

    @Override
    @AIClick({R.id.activity_main_e_tv})
    public void onClickCallbackSample(View view) {
        switch (view.getId()) {
            case R.id.activity_main_e_tv:
                showToastMessage("e clicked");
                break;
            default:
                break;
        }
    }


    @Override
    public void onWaveTouchUp(View view, Point locationInView, Point locationInScreen) {
        switch (view.getId()) {
            case R.id.activity_main_a_tv:
                WaveCompat.startWaveFilter(this,
                        new WaveDrawable()
                                .setColor(0xddffffff)
                                .setTouchPoint(locationInScreen),
                        generateIntent(0xddffffff));
                break;
            case R.id.activity_main_b_tv:
                WaveCompat.startWaveFilter(this,
                        new WaveDrawable()
                                .setColor(0xffFFC125)
                                .setTouchPoint(locationInScreen),
                        generateIntent(0xffFFC125));
                break;
            case R.id.activity_main_c_tv:
                WaveCompat.startWaveFilter(this,
                        new WaveDrawable()
                                .setColor(0xff8B2252)
                                .setTouchPoint(locationInScreen),
                        generateIntent(0xff8B2252));
                break;
            case R.id.activity_main_d_tv:
                WaveCompat.startWaveFilter(this,
                        new WaveDrawable()
                                .setColor(0xdd575757)
                                .setTouchPoint(locationInScreen),
                        generateIntent(0xdd575757));
                break;
            case R.id.activity_main_e_tv:
                WaveCompat.startWaveFilter(this,
                        new WaveDrawable()
                                .setColor(0xff218868)
                                .setTouchPoint(locationInScreen),
                        new Intent(context, SecondWareTestActivity.class),
                        0xff218868);
                break;
            default:
                break;
        }
    }

    private Intent generateIntent(int color) {
        Intent intent = new Intent(context, SecondWareTestActivity.class);
        intent.putExtra(WaveCompat.IntentKey.BACKGROUND_COLOR, color);
        return intent;
    }
}
