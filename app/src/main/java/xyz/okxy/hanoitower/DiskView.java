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
 * 用于动态生成的圆盘View
 * @author zcp
 * @since 2018/9/7
 */
public class DiskView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mIndex;

    public DiskView(Context context, int index) {
        super(context);
        mContext = context;
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
        // 圆盘生成基点在屏幕宽度1/4处
        float basePoint =  mContext.getResources()
                .getDisplayMetrics().widthPixels / 4f;
        /*
         * 圆盘索引越大圆盘越宽
         * 圆盘初始宽度
         */
        float left = basePoint - 60 + mIndex * 10;
        float right = basePoint + 60 - mIndex * 10;

        /*
         * 圆盘索引越大圆盘越高
         * 圆盘初始高度
        */
        float top = getTop() + 355 - mIndex * 20;
        float bottom = getTop() + 365 - mIndex * 20;

        // 绘制
        canvas.drawRoundRect(left, top, right, bottom,
                20f, 20f, mPaint);
    }

}
