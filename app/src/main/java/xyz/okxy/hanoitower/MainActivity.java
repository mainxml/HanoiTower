package xyz.okxy.hanoitower;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 获取三根柱子View的实例对象并以其宽度来初始化属性动画对象
//        final ThreePillarsView threePillarsView = findViewById(R.id.three_pillars_view);
//
//        // 层数输入框
//        final EditText diskCountEditText = findViewById(R.id.disk_count);
//
//        // 初始化开始按钮和设置点击监听事件
//        Button button = findViewById(R.id.start_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 获取用户输入的汉诺塔层数
//                String userInput = diskCountEditText.getText().toString();
//                if (!TextUtils.isEmpty(userInput)) {
//                    int diskCount = Integer.parseInt(userInput);
//                    threePillarsView.run(diskCount);
//                    // 隐藏软键盘
//                    ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager
//                                    .HIDE_NOT_ALWAYS);
//                } else {
//                    threePillarsView.run(3);
//                }
//            }
//        });

        setContentView(R.layout.activity_main2);
        HanoiTower hanoiTower = findViewById(R.id.hanoiTower);
        hanoiTower.setOnClickListener(v -> hanoiTower.start());
    }
}
