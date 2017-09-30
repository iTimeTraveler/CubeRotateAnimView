package io.itimetraveler.cube.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import io.itimetraveler.cube.anim.CubeInAnimation;
import io.itimetraveler.cube.anim.CubeOutAnimation;


/**
 * Created by iTimeTraveler on 2017/7/19.
 *
 * 立方旋转动画
 * 支持左右（左出右进，左进右出）、上下（上出下进，上进下出）旋转
 */

public class CubeRotateLayout extends FrameLayout {
    public static final int DIRECT_LEFT = 1;
    public static final int DIRECT_RIGHT = 2;
    public static final int DIRECT_TOP = 3;
    public static final int DIRECT_BOTTOM = 4;
    public static final int BITMAP_COMPRESS_SIZE = 2048;

    private Bitmap mSwitchOutBitmap;
    private Bitmap mSwitchInBitmap;
    private RotateAnimListener animListener;
    private int duration = 500;
    private boolean rotating;

    public CubeRotateLayout(Context context) {
        super(context);
    }

    public CubeRotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CubeRotateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwitchOutBitmap(Bitmap switchOutBitmap) {
        if(mSwitchOutBitmap != null){
            mSwitchOutBitmap.recycle();
        }
        this.mSwitchOutBitmap = switchOutBitmap;
    }

    public void setSwitchInBitmap(Bitmap switchInBitmap) {
        if(mSwitchInBitmap != null){
            mSwitchInBitmap.recycle();
        }
        this.mSwitchInBitmap = switchInBitmap;
    }

    public void setAnimDuration(int duration){
        this.duration = duration;
    }

    public void setAnimListener(RotateAnimListener animListener) {
        this.animListener = animListener;
    }

    /**
     * 立方旋转动画
     *
     * @param isLeftOrDownOut  true:左出右进、或上出下进, false:左进右出、或上进下出
     * @param isLeftRightAnim true:左右旋转, false:上下旋转
     */
    public void startRotateAnim(boolean isLeftOrDownOut, boolean isLeftRightAnim){
        if(rotating || mSwitchOutBitmap == null || mSwitchInBitmap == null ||
                mSwitchOutBitmap.isRecycled() || mSwitchInBitmap.isRecycled()){
            this.setVisibility(View.GONE);
            return;
        }
        rotating = true;
        this.setVisibility(View.VISIBLE);
        ImageView nextView = (ImageView) getChildAt(0);
        ImageView currentView = (ImageView) getChildAt(1);
        currentView.setScaleType(ImageView.ScaleType.FIT_XY);
        nextView.setScaleType(ImageView.ScaleType.FIT_XY);
        currentView.setImageBitmap(mSwitchOutBitmap);
        nextView.setImageBitmap(mSwitchInBitmap);

        CubeInAnimation inAnimation;
        CubeOutAnimation outAnimation;
        if (isLeftRightAnim) {      //左右旋转
            inAnimation = new CubeInAnimation(isLeftOrDownOut ? DIRECT_RIGHT : DIRECT_LEFT);
            outAnimation = new CubeOutAnimation(isLeftOrDownOut ? DIRECT_LEFT : DIRECT_RIGHT);
        } else {      //上下旋转
            inAnimation = new CubeInAnimation(isLeftOrDownOut ? DIRECT_TOP : DIRECT_BOTTOM);
            outAnimation = new CubeOutAnimation(isLeftOrDownOut ? DIRECT_BOTTOM : DIRECT_TOP);
        }
        inAnimation.setDuration(duration);
        inAnimation.setFillAfter(true);
        outAnimation.setDuration(duration);
        outAnimation.setFillAfter(true);
        nextView.startAnimation(inAnimation);
        currentView.startAnimation(outAnimation);
        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onAnimStart();
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                onAnimEnd();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 图片旋转变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        System.gc();
        return newBM;
    }


    private void onAnimStart(){
        if(animListener != null){
            animListener.onAnimationStart();
        }
    }

    private void onAnimEnd(){
        if(mSwitchOutBitmap != null){
            mSwitchOutBitmap.recycle();
            mSwitchOutBitmap = null;
        }
        if(mSwitchInBitmap != null){
            mSwitchInBitmap.recycle();
            mSwitchInBitmap = null;
        }
        System.gc();
        if(animListener != null){
            animListener.onAnimationEnd();
        }
        rotating = false;
    }

    public interface RotateAnimListener{
        void onAnimationStart();
        void onAnimationEnd();
    }

}
