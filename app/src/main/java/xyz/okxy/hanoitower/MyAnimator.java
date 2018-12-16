package xyz.okxy.hanoitower;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 属性动画-圆盘移动的所有动画在此
 * @author zcp
 * @since 2018/9/7
 */
public class MyAnimator {

    // 动画速度
    private int mDuration = 200;
    // 动画次数
    private int mMoveCount = 0;
    // 横向移动圆盘动画的距离
    private float mLen;

    public MyAnimator(float len) {
        mLen = len / 4;
    }

    public void aToB(View view) {
        moveUp(view);
        moveToB(view);
        moveDown(view);
    }

    public void aToC(View view) {
        moveUp(view);
        moveToC(view);
        moveDown(view);
    }


    public void bToC(View view) {
        moveUp(view);
        moveToC(view);
        moveDown(view);
    }

    public void bToA(View view) {
        moveUp(view);
        moveToA(view);
        moveDown(view);
    }

    public void cToA(View view) {
        moveUp(view);
        moveToA(view);
        moveDown(view);
    }

    public void cToB(View view) {
        moveUp(view);
        moveToB(view);
        moveDown(view);
    }

    /** 向上移动 */
    private void moveUp(View view) {
        ObjectAnimator moveUp = ObjectAnimator
                .ofFloat(view, "translationY", -320f)
                .setDuration(mDuration);
        moveUp.setStartDelay((++mMoveCount)*mDuration);
        moveUp.start();
    }

    /** 向下移动 */
    private void moveDown(View view) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(view, "translationY", 0f)
                .setDuration(mDuration);
        moveLeft.setStartDelay((++mMoveCount)*mDuration);
        moveLeft.start();
    }

    /** 移动到 A */
    private void moveToA(View view) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(view, "translationX", 0)
                .setDuration(mDuration);
        moveLeft.setStartDelay((++mMoveCount)*mDuration);
        moveLeft.start();
    }

    /** 移动到 B */
    private void moveToB(View view) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(view, "translationX", mLen)
                .setDuration(mDuration);
        moveRight.setStartDelay((++mMoveCount)*mDuration);
        moveRight.start();
    }

    /** 移动到 C */
    private void moveToC(View view) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(view, "translationX", 2*mLen)
                .setDuration(mDuration);
        moveRight.setStartDelay((++mMoveCount)*mDuration);
        moveRight.start();
    }

    public void resetMoveCount() {
        mMoveCount = 0;
    }
}
