package io.itimetraveler.cube.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.widget.FrameLayout;

public class CubeLayoutRevert extends FrameLayout {

    private Camera mCamera;
    private Matrix mMatrix;
    private static final int sFinalDegree = 90;
    private BaseInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private long mDuration = 1000;
    private long mStartTime;

    private boolean mIsAnimating = true;

    public CubeLayoutRevert(Context context) {
        this(context, null);
    }

    public CubeLayoutRevert(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CubeLayoutRevert(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mCamera = new Camera();
        this.mMatrix = new Matrix();
        this.mStartTime = System.currentTimeMillis();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsAnimating) {
            long currentTime = System.currentTimeMillis();
            long interpolationTime = currentTime - mStartTime;
            double interpolation = mInterpolator.getInterpolation(interpolationTime * 1.0f / mDuration);
            cube(canvas, interpolation);
            if (interpolationTime < mDuration) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            super.dispatchDraw(canvas);
        }
    }


    private void cube(Canvas canvas, double interpolation) {
        View foregroundView = getChildAt(0);    //绿色
        View backgroundView = getChildAt(1);    //红色
        int width = getWidth();
        int height = getHeight();
        long drawingTime = getDrawingTime();
        float rotate;

//        rotate = (float)(- sFinalDegree * interpolation);
//        mCamera.save();
//        mCamera.translate((float)(width - interpolation * width), 0, 0);
//        mCamera.rotateY(rotate);
//        mCamera.getMatrix(mMatrix);
//        mCamera.restore();
//
//        mMatrix.postTranslate(0, height / 2);
//        mMatrix.preTranslate(-width, -height / 2);
//        canvas.save();
//        canvas.concat(mMatrix);
//        drawChild(canvas, foregroundView, drawingTime);
//        canvas.restore();
//        //end drawForeground

        rotate = (float)(sFinalDegree * interpolation - sFinalDegree);
        mCamera.save();
        mCamera.translate((float)(interpolation * width), 0, 0);
        mCamera.rotateY(rotate);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.postTranslate(0, height / 2);
        mMatrix.preTranslate(-width, -height / 2);
        canvas.save();
        canvas.concat(mMatrix);
        drawChild(canvas, foregroundView, drawingTime);
        canvas.restore();
        //end drawForeground



        //draw Background
//        rotate = (float)(sFinalDegree - sFinalDegree * interpolation);
//        mCamera.save();
//        mCamera.translate((float)(-width * interpolation), 0, 0);
//        mCamera.rotateY(rotate);
//        mCamera.getMatrix(mMatrix);
//        mCamera.restore();
//
//        mMatrix.postTranslate(width, height / 2);
//        mMatrix.preTranslate(0, -height / 2);
//        canvas.save();
//        canvas.concat(mMatrix);
//        drawChild(canvas, backgroundView, drawingTime);
//        canvas.restore();
        //end draw Background


        //draw Background
        rotate = (float)(sFinalDegree * interpolation);
        mCamera.save();
        mCamera.translate((float)(-width + width * interpolation), 0, 0);
        mCamera.rotateY(rotate);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.postTranslate(width, height / 2);
        mMatrix.preTranslate(0, -height / 2);
        canvas.save();
        canvas.concat(mMatrix);
        drawChild(canvas, backgroundView, drawingTime);
        canvas.restore();
        //end draw Background



//        DecimalFormat df = new DecimalFormat("######0.0000");
//        double degree = Math.toRadians(sFinalDegree * interpolation);
//        degree = Double.valueOf(df.format(degree));
//
//
//        //draw Background
//        rotate = (float)(sFinalDegree * interpolation);
//        mCamera.save();
////        mCamera.translate((float)(-width * interpolation), 0, 0);
//        mCamera.translate((float)(width - width * Double.valueOf(df.format(Math.cos(degree)))), 0, 0);
//        mCamera.rotateY(rotate);
//        mCamera.getMatrix(mMatrix);
//        mCamera.restore();
//
//
//
//        Log.e("www,,,", sFinalDegree * interpolation +"");
//        Log.e("www", Double.valueOf(df.format(Math.cos(degree))) +"");
//        Log.e("www", (float) (width - width * Double.valueOf(df.format(Math.cos(degree)))) +"");
//        mMatrix.postTranslate((float) (width - width * Double.valueOf(df.format(Math.cos(degree)))), height / 2);
//        mMatrix.preTranslate(0, -height / 2);
//        canvas.save();
//        canvas.concat(mMatrix);
//        drawChild(canvas, backgroundView, drawingTime);
//        canvas.restore();
        //end draw Background

    }

}
