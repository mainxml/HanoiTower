package xyz.okxy.hanoitower;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

/**
 * 属性动画
 * @author zcp
 * @since 2018/9/7
 */
public class MyAnimator {

    private Context mContext;
    private int mDuration = 200;
    private int mMoveCount = 1;
    private float basePoint;

    public MyAnimator(Context context) {
        mContext = context;
        basePoint = mContext.getResources().getDisplayMetrics().widthPixels / 4f;
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
                .ofFloat(view, "translationY", -160f)
                .setDuration(mDuration);
        moveUp.setStartDelay((mMoveCount++)*mDuration);
        moveUp.start();
    }

    /** 向下移动 */
    private void moveDown(View view) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(view, "translationY", 0f)
                .setDuration(mDuration);
        moveLeft.setStartDelay((mMoveCount++)*mDuration);
        moveLeft.start();
    }

    /** 移动到 A */
    private void moveToA(View view) {
        ObjectAnimator moveLeft = ObjectAnimator
                .ofFloat(view, "translationX", 0)
                .setDuration(mDuration);
        moveLeft.setStartDelay((mMoveCount++)*mDuration);
        moveLeft.start();
    }

    /** 移动到 B */
    private void moveToB(View view) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(view, "translationX", basePoint)
                .setDuration(mDuration);
        moveRight.setStartDelay((mMoveCount++)*mDuration);
        moveRight.start();
    }

    /** 移动到 C */
    private void moveToC(View view) {
        ObjectAnimator moveRight = ObjectAnimator
                .ofFloat(view, "translationX", 2*basePoint)
                .setDuration(mDuration);
        moveRight.setStartDelay((mMoveCount++)*mDuration);
        moveRight.start();
    }

}
