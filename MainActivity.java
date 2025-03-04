package com.example.stopwatchh;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.stopwatchh.R;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    MaterialButton reset, start, stop;
    int seconds, minutes, milliSec;
    long millisec, startTime, timeBuff, updateTime = 0L;

    Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisec = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisec;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSec = (int) (updateTime % 1000);

            textView.setText(MessageFormat.format("{0}:{1}:{2}" , minutes,  String.format(Locale.getDefault(),"%02d", seconds),String.format(Locale.getDefault(), "%02d", milliSec)));
            handler.postDelayed(this, 0);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        reset = findViewById(R.id.reset);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        handler = new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock. uptimeMillis();
                handler.postDelayed(runnable, 0 );
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += millisec;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                millisec = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime =  0L;
                seconds = 0;
                minutes = 0;
                milliSec = 0;
                textView.setText("00:00:00");
            }
        });

        textView.setText("00:00:00");
    }
}