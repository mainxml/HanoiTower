package com.mainxml.hanoitower;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HanoiTower hanoiTower = findViewById(R.id.hanoiTower);
        EditText countView = findViewById(R.id.etView);
        Button startButton = findViewById(R.id.btnStart);
        startButton.setOnClickListener(v -> {
            String stringCount = countView.getText().toString();
            int finalCount = 3;
            if (!TextUtils.isEmpty(stringCount)) {
                finalCount = Integer.parseInt(stringCount);
            }
            hanoiTower.setDiskCount(finalCount);
            hanoiTower.start();
        });
    }
}
