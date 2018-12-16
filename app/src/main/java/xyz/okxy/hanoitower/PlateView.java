package xyz.okxy.hanoitower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * PlateView
 * 汉诺塔圆盘View
 * @author zcp
 * @since 2018/9/7
 */
public class PlateView extends View {

    // 横向移动圆盘动画的距离
    private ThreePillarsView mParent;
    private Paint mPaint;
    // 圆盘索引
    private int mIndex;

    public PlateView(Context context) {
        super(context);
    }

    public PlateView(Context context, ThreePillarsView parent, int index) {
        this(context);
        mParent = parent;
        mPaint = new Paint();
        Random random = new Random();
        int color = Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
        mPaint.setColor(color);
        mIndex = index;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 圆盘生成基点在三根柱子1/4处
        float basePoint = mParent.getRealWidth() / 4f + mParent.getRealLeft()*2 + 2.5f;

        //圆盘初始宽度,索引越大圆盘越宽
        float left = basePoint - 80 + mIndex * 10;
        float right = basePoint + 80 - mIndex * 10;

        // 圆盘初始高度,索引越大圆盘越高
        float top = mParent.getRealBottom() - 45 - mIndex * 20;
        float bottom = mParent.getRealBottom() - 35 - mIndex * 20;

        // 绘制
        canvas.drawRoundRect(left, top, right, bottom,
                         10f, 10f, mPaint);
    }

}
