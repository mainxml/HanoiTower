package xyz.okxy.hanoitower;

import android.animation.ObjectAnimator;

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
    // 柱子间距离
    private float mPillarDistance;

    public MyAnimator(float ThreePillarWidth) {
        mPillarDistance = ThreePillarWidth / 4;
    }

    public void aToB(PlateView plateView) {
        moveUp(plateView);
        moveToB(plateView);
        moveDown(plateView);
    }

    public void aToC(PlateView plateView) {
        moveUp(plateView);
        moveToC(plateView);
        moveDown(plateView);
    }


    public void bToC(PlateView plateView) {
        moveUp(plateView);
        moveToC(plateView);
        moveDown(plateView);
    }

    public void bToA(PlateView plateView) {
        moveUp(plateView);
        moveToA(plateView);
        moveDown(plateView);
    }

    public void cToA(PlateView plateView) {
        moveUp(plateView);
        moveToA(plateView);
        moveDown(plateView);
    }

    public void cToB(PlateView plateView) {
        moveUp(plateView);
        moveToB(plateView);
        moveDown(plateView);
    }

    // 向上移动
    private void moveUp(PlateView plateView) {
        ObjectAnimator moveUp = ObjectAnimator
                .ofFloat(plateView, "translationY", -(plateView.getRealTop()-50))
                .setDuration(mDuration);
        moveUp.setStartDelay((++mMoveCount) * mDuration);
        moveUp.start();
    }

    // 下移动
    private void moveDown(PlateView plateView) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(plateView, "translationY", 0f)
                .setDuration(mDuration);
        moveLeft.setStartDelay(++mMoveCount * mDuration);
        moveLeft.start();
    }

    // 移动到 A 柱
    private void moveToA(PlateView plateView) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(plateView, "translationX", 0f)
                .setDuration(mDuration);
        moveLeft.setStartDelay(++mMoveCount * mDuration);
        moveLeft.start();
    }

    // 移动到 B 柱
    private void moveToB(PlateView plateView) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(plateView, "translationX", mPillarDistance)
                .setDuration(mDuration);
        moveRight.setStartDelay(++mMoveCount * mDuration);
        moveRight.start();
    }

    // 移动到 C 柱
    private void moveToC(PlateView plateView) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(plateView, "translationX", 2 * mPillarDistance)
                .setDuration(mDuration);
        moveRight.setStartDelay(++mMoveCount * mDuration);
        moveRight.start();
    }
}
