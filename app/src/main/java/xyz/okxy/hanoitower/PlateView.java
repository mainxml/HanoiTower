package xyz.okxy.hanoitower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * PlateView
 * 汉诺塔圆盘View
 * @author zcp
 * @since 2018/9/7
 */
public class PlateView extends View {

    /** 三根柱子view,根据它来决定圆盘的位置 */
    private ThreePillarsView mParent;
    /** 圆盘索引，索引越小表示圆盘越在下 */
    private int mIndex;
    /** 画笔 */
    private Paint mPaint;

    public PlateView(Context context) {
        super(context);
    }

    /**
     * 初始化圆盘
     * @param context 上下文
     * @param parent  三根柱子View
     * @param index   此圆盘对于三根柱子的索引
     */
    public PlateView(Context context, ThreePillarsView parent, int index) {
        this(context);
        mParent = parent;
        mPaint = new Paint();
        // 让圆盘颜色鲜艳点
        Random random = new Random();
        int r, g, b;
        do {
            r = random.nextInt(256);
            g = random.nextInt(256);
            b = random.nextInt(256);
        } while (r - g - b < 0);
        int color = Color.rgb(r, g, b);
        mPaint.setColor(color);
        mIndex = index;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 圆盘生成基点在三根柱子1/4处
        float basePoint = mParent.getRealWidth() / 4f + mParent.getRealLeft() + 5;

        //圆盘初始宽度,索引越大圆盘越宽
        float left = basePoint - 80 + mIndex * 12;
        float right = basePoint + 80 - mIndex * 12;

        // 圆盘初始高度,索引越大圆盘越高
        float top = mParent.getRealBottom() - 30 - mIndex * 22;
        float bottom = mParent.getRealBottom() - 15 - mIndex * 22;

        // 绘制
        canvas.drawRoundRect(left, top, right, bottom, 10f, 10f, mPaint);
    }

    /** 返回此圆盘对于三根柱子的索引 */
    public int getIndex() {
        return mIndex;
    }

    public float getRealTop() {
        return mParent.getRealBottom() - 30 - mIndex * 22;
    }
}
