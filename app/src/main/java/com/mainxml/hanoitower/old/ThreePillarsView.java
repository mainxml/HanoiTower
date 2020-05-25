package com.mainxml.hanoitower.old;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mainxml.hanoitower.PillarStack;

import java.util.ArrayList;
import java.util.List;

/**
 * ThreePillarsView
 * 汉诺塔三根柱子View
 *
 * @author zcp
 * @since 2018/9/7
 */
public class ThreePillarsView extends View {

    private final String PILLAR_A = "A";
    private final String PILLAR_B = "B";
    private final String PILLAR_C = "C";

    /** 柱子 A */
    private PillarStack<PlateView> A = new PillarStack<>(PILLAR_A);

    /** 柱子 B */
    private PillarStack<PlateView> B = new PillarStack<>(PILLAR_B);

    /** 柱子 C */
    private PillarStack<PlateView> C = new PillarStack<>(PILLAR_C);

    /** 所有圆盘列表 */
    private List<PlateView> mPlateViewList = new ArrayList<>();

    private FrameLayout mRootLayout;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    /** 属性动画实例 */
    private MyAnimator mAnimator;

    // 真正绘制的四个位置
    private float mRealLeft;
    private float mRealTop;
    private float mRealRight;
    private float mRealBottom;

    public ThreePillarsView(Context context) {
        super(context);
        init();
    }

    public ThreePillarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setColor(Color.GRAY);
        mPath = new Path();
        mRootLayout = ((Activity)getContext()).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 内边距
        float paddingTop = getPaddingTop();
        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingBottom = getPaddingBottom();

        mRealTop = getTop() + paddingTop;
        mRealLeft = getLeft() + paddingLeft;
        mRealRight = getRight() - paddingRight;
        mRealBottom = getBottom() - paddingBottom;

        mPath.moveTo(mRealLeft, mRealTop);      // 开始点左上
        mPath.lineTo(mRealRight, mRealTop);     // 画线到右上
        mPath.lineTo(mRealRight, mRealBottom);  // 画线到右下
        mPath.lineTo(mRealLeft, mRealBottom);   // 画线到左下
        mPath.lineTo(mRealLeft, mRealTop);      // 画线到左上
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //-------------------------------------------------------------------------
        //- 绘制四个点
        //-------------------------------------------------------------------------
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPath, mPaint);

        //-------------------------------------------------------------------------
        //- 画三根柱子
        //-------------------------------------------------------------------------
        mPaint.setStyle(Paint.Style.FILL);
        float len = (mRealRight - mRealLeft) / 4f;

//        // 第一根柱子
//        canvas.drawRoundRect(mRealLeft+len, mRealTop+100,
//                mRealLeft+len+10, mRealBottom-30,
//                10f, 10f, mPaint);
//
//        // 第二根柱子
//        canvas.drawRoundRect(mRealLeft+2*len, mRealTop+100,
//                mRealLeft+2*len+10, mRealBottom-30,
//                10f, 10f, mPaint);
//
//        // 第三根柱子
//        canvas.drawRoundRect(mRealLeft+3*len, mRealTop+100,
//                mRealLeft+3*len+10, mRealBottom-30,
//                10f, 10f, mPaint);
//
//        // 柱子的底盘
//        canvas.drawRoundRect(mRealLeft+50, mRealBottom-45,
//                mRealRight-50, mRealBottom-30,
//                10f, 10f, mPaint);
        // 第一根柱子
        canvas.drawRoundRect(mRealLeft+len, mRealTop+100,
                mRealLeft+len+10, mRealBottom,
                10f, 10f, mPaint);

        // 第二根柱子
        canvas.drawRoundRect(mRealLeft+2*len, mRealTop+100,
                mRealLeft+2*len+10, mRealBottom,
                10f, 10f, mPaint);

        // 第三根柱子
        canvas.drawRoundRect(mRealLeft+3*len, mRealTop+100,
                mRealLeft+3*len+10, mRealBottom,
                10f, 10f, mPaint);

        // 柱子的底盘
        canvas.drawRoundRect(mRealLeft, mRealBottom-10,
                mRealRight, mRealBottom,
                10f, 10f, mPaint);
    }

    public void run(int pillarCount) {
        if (pillarCount >= 8) {
            Toast.makeText(getContext(), "超过8层就画不下啦！理论上可以运行无限层！", Toast.LENGTH_LONG).show();
            return;
        }

        mAnimator = new MyAnimator(getRealWidth());

        // 清除所有圆盘和柱子上的圆盘
        for (int i = 0; i < mPlateViewList.size(); i++) {
            mRootLayout.removeView(mPlateViewList.get(i));
            A.clear(); B.clear(); C.clear();
        }

        // 初始化三个圆盘到A柱子
        for (int i = 0; i < pillarCount; i++) {
            //索引越大 圆盘越小（越在上）
            PlateView plateView = new PlateView(getContext(), this, i);
            mPlateViewList.add(plateView);
            // 添加圆盘到布局
            mRootLayout.addView(plateView);
            // 圆盘加入A柱子
            A.push(plateView);
        }

        // 根据算法开始执行动画！
        hanoiTower(pillarCount, A, B, C);
    }

    /** 汉诺塔算法 */
    public void hanoiTower(int step, PillarStack<PlateView> A, PillarStack<PlateView> B, PillarStack<PlateView> C) {
        if (step == 1) {
            move(A, C);
        } else if (step > 1) {
            hanoiTower(step-1, A, C, B);
            move(A, C);
            hanoiTower(step-1, B, A, C);
        }
    }

    /** 移动圆盘 */
    private void move(PillarStack<PlateView> from, PillarStack<PlateView> to) {
        PlateView popPlateView = from.pop();
        String fromName = from.getName();
        String toName = to.getName();

        switch (fromName) {
            case PILLAR_A:
                if (PILLAR_B.equals(toName)) {
                    mAnimator.aToB(popPlateView);
                } else {
                    mAnimator.aToC(popPlateView);
                }
                break;
            case PILLAR_B:
                if (PILLAR_A.equals(toName)) {
                    mAnimator.bToA(popPlateView);
                } else {
                    mAnimator.bToC(popPlateView);
                }
                break;
            case PILLAR_C:
                if (PILLAR_A.equals(toName)) {
                    mAnimator.cToA(popPlateView);
                } else {
                    mAnimator.cToB(popPlateView);
                }
                break;
            default:
                break;
        }

        to.push(popPlateView);
        Log.i("move", fromName + "->" + toName);
    }

    /** 测量宽度，值为wrap_content时宽度等于屏幕宽度 */
    private int measureWidth(int widthMeasureSpec) {
        return MeasureSpec.getSize(widthMeasureSpec);
    }

    /** 测量高度，值为wrap_content时高度等于500px */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 500;
        }
        return result;
    }

    public float getRealWidth() {
        return mRealRight - mRealLeft;
    }

    public float getRealLeft() {
        return mRealLeft;
    }

    public float getRealTop() {
        return mRealTop;
    }

    public float getRealRight() {
        return mRealRight;
    }

    public float getRealBottom() {
        return mRealBottom;
    }
}
