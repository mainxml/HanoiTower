package xyz.okxy.hanoitower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * BackgroundView
 * 汉诺塔背景View（三个柱子）
 * @author zcp
 * @since 2018/9/7
 */
public class BackgroundView extends View {

    private Paint mPaint;

    public BackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float basePoint = getWidth() / 4f;

        canvas.drawRoundRect(basePoint-5, getBottom()-180,
                             basePoint+5, getBottom()-10,
                             20f, 20f, mPaint);

        canvas.drawRoundRect(2*basePoint-5, getBottom()-180,
                             2*basePoint+5, getBottom()-10,
                             20f, 20f, mPaint);

        canvas.drawRoundRect(3*basePoint-5, getBottom()-180,
                             3*basePoint+5, getBottom()-10,
                             20f, 20f, mPaint);

        canvas.drawRoundRect(getLeft()+150, getBottom()-20,
                             getRight()-150, getBottom()-10,
                             20f, 20f, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension
                (measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /** 测量宽，wrap_content默认为屏幕宽度 */
    private int measureWidth(int widthMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        return size;
    }

    /** 测量高，wrap_content默认为400 */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                result = 400;
                break;
        }
        return result;
    }

}
