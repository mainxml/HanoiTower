package xyz.okxy.hanoitower;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MainActivity
 * @author zcp
 * @since 2018/9/7
 */
public class MainActivity extends AppCompatActivity {

    /** Activity 根布局 */
    private FrameLayout mLayout;
    /** 所有圆盘列表 */
    private List<DiskView> mDiskViewList = new ArrayList<>();
    /** 属性动画 */
    private MyAnimator mAnimator;

    /** 柱子 A */
    private MyStack<DiskView> A = new MyStack<>("A");
    /** 柱子 B */
    private MyStack<DiskView> B = new MyStack<>("B");
    /** 柱子 C */
    private MyStack<DiskView> C = new MyStack<>("C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化布局
        mLayout = findViewById(R.id.layout);
        // 层数输入框
        final EditText diskCountEditText = findViewById(R.id.disk_count);
        mAnimator = new MyAnimator(this);

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
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getApplication().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow
                            (v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                if (diskCount >= 8) {
                    Toast.makeText(MainActivity.this,
                            "超过8层就画不下啦！理论上可以运行无限层！",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // 清除所有圆盘和柱子上的圆盘
                for (int i = 0; i < mDiskViewList.size(); i++) {
                    mLayout.removeView(mDiskViewList.get(i));
                    mAnimator = new MyAnimator(MainActivity.this);
                    A.clear(); B.clear(); C.clear();
                }

                // 初始化三个圆盘到A柱子
                for (int i = 0; i < diskCount; i++) {
                    //索引越大 圆盘越小（越在上）
                    DiskView diskView = new DiskView(MainActivity.this,  i);
                    mDiskViewList.add(diskView);
                    // 添加圆盘到布局
                    mLayout.addView(diskView);
                    // 圆盘加入A柱子
                    A.push(diskView);
                }

                // 根据算法步骤执行动画
                hanoiTower(diskCount, A, B, C);

            }
        });
    }

    /** 汉诺塔算法 */
    private void hanoiTower(int n, MyStack<DiskView> A, MyStack<DiskView> B, MyStack<DiskView> C) {
        if (n >= 2) {
            hanoiTower(n-1, A, C, B);
            move(A, C);
            hanoiTower(n-1, B, A, C);
        }

        if (n == 1) { move(A, C); }
    }

    private void move(MyStack<DiskView> from, MyStack<DiskView> to) {
        DiskView popDiskView = from.pop();

        String fromName = from.getName();
        String toName = to.getName();

        switch (fromName) {
            case "A":
                if ("B".equals(toName)) { mAnimator.aToB(popDiskView); }
                else                    { mAnimator.aToC(popDiskView); }
                break;
            case "B":
                if ("A".equals(toName)) { mAnimator.bToA(popDiskView); }
                else                    { mAnimator.bToC(popDiskView); }
                break;
            case "C":
                if ("A".equals(toName)) { mAnimator.cToA(popDiskView); }
                else                    { mAnimator.cToB(popDiskView); }
                break;
            default: break;
        }

        to.push(popDiskView);
        Log.i("move", fromName + "->" + toName);
    }

}
