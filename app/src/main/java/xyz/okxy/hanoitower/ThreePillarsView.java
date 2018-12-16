package xyz.okxy.hanoitower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * ThreePillarsView
 * 汉诺塔三根柱子View
 * @author zcp
 * @since 2018/9/7
 */
public class ThreePillarsView extends View {

    private Paint mPaint;

    // 真正绘制的四个点
    private float mRealLeft;
    private float mRealTop;
    private float mRealRight;
    private float mRealBottom;

    public ThreePillarsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 内边距
        float paddingLeft = getPaddingLeft();
        float paddingTop = getPaddingTop();
        float paddingRight = getPaddingRight();
        float paddingBottom = getPaddingBottom();

        // 外边距
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
        float leftMargin = layoutParams.leftMargin;
        float rightMargin = layoutParams.rightMargin;
        float topMargin = layoutParams.topMargin;
        float bottomMargin = layoutParams.bottomMargin;

        // 真正绘制的四个点
        mRealLeft = getLeft() + paddingLeft;
        mRealTop = getTop() + paddingTop;
        mRealRight = getRight() - paddingRight - leftMargin - rightMargin;
        mRealBottom = getBottom() - paddingBottom - topMargin - bottomMargin;

        Path path = new Path();
        path.moveTo(mRealLeft, mRealTop);      // 点移动到左上
        path.lineTo(mRealRight, mRealTop);     // 点移动到右上
        path.lineTo(mRealRight, mRealBottom);  // 点移动到右下
        path.lineTo(mRealLeft, mRealBottom);   // 点移动到左下
        path.lineTo(mRealLeft, mRealTop);      // 点移动到左上

        //mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        //-------------------------------------------------------------------------
        //-- 开始画柱子
        //-------------------------------------------------------------------------
        // 画笔填充
        mPaint.setStyle(Paint.Style.FILL);
        //分割长度
        float len = (mRealRight - mRealLeft) / 4f;

        // 第一根柱子
        canvas.drawRoundRect(mRealLeft+len, mRealTop+100,
                             mRealLeft+len+5, mRealBottom-30,
                             10f, 10f, mPaint);

        // 第二根柱子
        canvas.drawRoundRect(mRealLeft+2*len, mRealTop+100,
                             mRealLeft+2*len+5, mRealBottom-30,
                             10f, 10f, mPaint);

        // 第三根柱子
        canvas.drawRoundRect(mRealLeft+3*len, mRealTop+100,
                             mRealLeft+3*len+5, mRealBottom-30,
                             10f, 10f, mPaint);

        // 三根柱子的底盘
        canvas.drawRoundRect(mRealLeft+50, mRealBottom-45,
                             mRealLeft+mRealRight-50, mRealBottom-30,
                             10f, 10f, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /** 测量宽度，wrap_content的时候默认为屏幕宽度 */
    private int measureWidth(int widthMeasureSpec) {
        return MeasureSpec.getSize(widthMeasureSpec);
    }

    /** 测量高度，wrap_content时默认为500 */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                result = 500;
                break;
        }
        return result;
    }

    /**
     * 返回处理过（内网边距）的真正宽度
     * @return
     */
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
