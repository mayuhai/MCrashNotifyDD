package com.hunter.mcrashnotifyddtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hunter.tracelog.crash.PerformanceReportManager;
import com.hunter.tracelog.save.BaseTraceSaver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 1/0;
            }
        });
    }
}
