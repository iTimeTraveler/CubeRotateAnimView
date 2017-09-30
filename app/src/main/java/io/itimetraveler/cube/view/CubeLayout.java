package io.itimetraveler.cube.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.widget.FrameLayout;

import io.itimetraveler.cube.anim.CubeDownInAnimation;
import io.itimetraveler.cube.anim.CubeUpOutAnimation;

/**
 * Created by geminiwen on 15/9/24.
 */
public class CubeLayout extends FrameLayout{

    private BaseInterpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public CubeLayout(Context context) {
        this(context, null);
    }

    public CubeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CubeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View foregroundView = getChildAt(0);
        View backgroundView = getChildAt(1);

//        CubeRightInAnimation rightInAnimation = new CubeRightInAnimation();
//        rightInAnimation.setDuration(1000);
//        rightInAnimation.setFillAfter(true);
//
//        CubeLeftOutAnimation leftOutAnimation = new CubeLeftOutAnimation();
//        leftOutAnimation.setDuration(1000);
//        leftOutAnimation.setFillAfter(true);
//
//        foregroundView.startAnimation(rightInAnimation);
//        backgroundView.startAnimation(leftOutAnimation);

        CubeDownInAnimation downInAnimation = new CubeDownInAnimation();
        downInAnimation.setDuration(5000);
        downInAnimation.setFillAfter(true);

        CubeUpOutAnimation upOutAnimation = new CubeUpOutAnimation();
        upOutAnimation.setDuration(5000);
        upOutAnimation.setFillAfter(true);

        foregroundView.startAnimation(downInAnimation);
        backgroundView.startAnimation(upOutAnimation);
    }
}

