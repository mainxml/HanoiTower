package xyz.okxy.hanoitower;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcp
 * @since 2018/9/7
 */
public class MainActivity extends AppCompatActivity {

    /** Activity 的根布局 */
    private FrameLayout mLayout;
    /** 所有圆盘视图列表 */
    private List<PlateView> mPlateViewList = new ArrayList<>();
    /** 属性动画 */
    private MyAnimator mAnimator;
    /** 三根柱子视图的宽 */
    private float mThreePillarsViewWidth;

    /** 柱子 A */
    private PillarStack<PlateView> A = new PillarStack<>("A");
    /** 柱子 B */
    private PillarStack<PlateView> B = new PillarStack<>("B");
    /** 柱子 C */
    private PillarStack<PlateView> C = new PillarStack<>("C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取三根柱子View的实例对象并以其宽度来初始化属性动画对象
        final ThreePillarsView threePillarsView = findViewById(R.id.three_pillars_view);
        final ViewTreeObserver observer = threePillarsView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mThreePillarsViewWidth = threePillarsView.getRealWidth();
                    mAnimator = new MyAnimator(mThreePillarsViewWidth);
                    Log.i("ThreePillarsView", "处理过（内网边距）的真正宽度" + mThreePillarsViewWidth);
                    if (observer.isAlive()) {
                        observer.removeOnGlobalLayoutListener(this);
                    }
                }
            }
        );

        // 初始化根布局
        mLayout = findViewById(R.id.layout);
        // 层数输入框
        final EditText diskCountEditText = findViewById(R.id.disk_count);

        // 初始化开始按钮和设置点击监听事件
        Button button = findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 圆盘数量
                int diskCount = 3;
                if (!"".equals(diskCountEditText.getText().toString())) {
                    diskCount = Integer.parseInt(diskCountEditText.getText().toString());

                    // 隐藏软键盘
                    ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                if (diskCount >= 8) {
                    Toast.makeText(MainActivity.this,
                            "超过8层就画不下啦！理论上可以运行无限层！",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // 清除所有圆盘和柱子上的圆盘
                for (int i = 0; i < mPlateViewList.size(); i++) {
                    mLayout.removeView(mPlateViewList.get(i));
                    mAnimator.resetMoveCount();
                    A.clear(); B.clear(); C.clear();
                }

                // 初始化三个圆盘到A柱子
                for (int i = 0; i < diskCount; i++) {
                    //索引越大 圆盘越小（越在上）
                    PlateView plateView = new PlateView(MainActivity.this, threePillarsView, i);
                    mPlateViewList.add(plateView);
                    // 添加圆盘到布局
                    mLayout.addView(plateView);
                    // 圆盘加入A柱子
                    A.push(plateView);
                }

                // 根据算法步骤执行动画
                hanoiTower(diskCount, A, B, C);

            }
        });
    }

    /** 汉诺塔算法 */
    private void hanoiTower(int n, PillarStack<PlateView> A, PillarStack<PlateView> B, PillarStack<PlateView> C) {
        if (n >= 2) {
            hanoiTower(n-1, A, C, B);
            move(A, C);
            hanoiTower(n-1, B, A, C);
        }

        if (n == 1) { move(A, C); }
    }

    private void move(PillarStack<PlateView> from, PillarStack<PlateView> to) {
        PlateView popPlateView = from.pop();

        String fromName = from.getName();
        String toName = to.getName();

        switch (fromName) {
            case "A":
                if ("B".equals(toName)) { mAnimator.aToB(popPlateView); }
                else                    { mAnimator.aToC(popPlateView); }
                break;
            case "B":
                if ("A".equals(toName)) { mAnimator.bToA(popPlateView); }
                else                    { mAnimator.bToC(popPlateView); }
                break;
            case "C":
                if ("A".equals(toName)) { mAnimator.cToA(popPlateView); }
                else                    { mAnimator.cToB(popPlateView); }
                break;
            default: break;
        }

        to.push(popPlateView);
        Log.i("move", fromName + "->" + toName);
    }

}
