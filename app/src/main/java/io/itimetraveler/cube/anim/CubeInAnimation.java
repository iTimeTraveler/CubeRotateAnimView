package io.itimetraveler.cube.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import io.itimetraveler.cube.view.CubeRotateLayout;

/**
 * Created by iTimeTraveler on 2017/7/19.
 */

public class CubeInAnimation extends Animation {
    private Camera mCamera;
    private Matrix mMatrix;
    private int mWidth;
    private int mHeight;
    private int outDirect;
    private static final int sFinalDegree = 90;

    public CubeInAnimation(int outDirect){
        this.outDirect = outDirect;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mMatrix = new Matrix();
        mWidth = width;
        mHeight = height;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float rotate = 0;
        float translateX = 0;
        float translateY = 0;
        switch (outDirect){
            case CubeRotateLayout.DIRECT_LEFT:
                //左进
                rotate = sFinalDegree * interpolatedTime - sFinalDegree;
                translateX = interpolatedTime * mWidth;
                break;
            case CubeRotateLayout.DIRECT_RIGHT:
                //右进
                rotate = (sFinalDegree - sFinalDegree * interpolatedTime);  //Camera旋转角度：90-0度
                translateX = -mWidth * interpolatedTime;        //Camera平移：0到-width
                break;
            case CubeRotateLayout.DIRECT_TOP:
                //上进
                rotate = sFinalDegree - sFinalDegree * interpolatedTime;
                translateY = -interpolatedTime * mHeight;
                break;
            case CubeRotateLayout.DIRECT_BOTTOM:
                //下进
                rotate = (sFinalDegree * interpolatedTime - sFinalDegree);  //Camera旋转角度：-90~0度
                translateY = mHeight * interpolatedTime;        //Camera平移：0到mHeight
                break;
            default:
                return;
        }

        //左右Camera旋转
        if(outDirect == CubeRotateLayout.DIRECT_LEFT || outDirect == CubeRotateLayout.DIRECT_RIGHT){
            mCamera.save();
            mCamera.translate(translateX, 0, 0);
            mCamera.rotateY(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
        } else {
            mCamera.save();
            mCamera.translate(0, translateY, 0);
            mCamera.rotateX(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
        }

        switch (outDirect){
            case CubeRotateLayout.DIRECT_LEFT:
                //左进
                mMatrix.postTranslate(0, mHeight / 2);
                mMatrix.preTranslate(- mWidth, - mHeight / 2);
                break;
            case CubeRotateLayout.DIRECT_RIGHT:
                //右进
                mMatrix.postTranslate(mWidth, mHeight / 2);
                mMatrix.preTranslate(0, - mHeight / 2);
                break;
            case CubeRotateLayout.DIRECT_TOP:
                //上进
                mMatrix.postTranslate(mWidth / 2, 0);
                mMatrix.preTranslate( - mWidth / 2, - mHeight);
                break;
            case CubeRotateLayout.DIRECT_BOTTOM:
                //下进
                mMatrix.postTranslate(mWidth / 2, mHeight);
                mMatrix.preTranslate(- mWidth / 2, 0);
                break;
        }

        t.getMatrix().postConcat(mMatrix);
    }
}
