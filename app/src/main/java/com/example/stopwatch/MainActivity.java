package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private TextView timerText,continuetxt,pausetxt,resettxt;
    private Button startButton, pauseButton, resetButton;

    private long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;
    private Handler handler = new Handler();
    private boolean isRunning = false;
    GifImageView clockgif;

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;

            int seconds = (int) (updateTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updateTime % 100);

            timerText.setText(String.format("%02d:%02d:%02d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 10); // Update every 10ms
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        clockgif = findViewById(R.id.cloclgif_id);
        continuetxt = findViewById(R.id.continuetxt_id);
        pausetxt = findViewById(R.id.pausetxt_id);
        resettxt = findViewById(R.id.resettxt_id);


//        pauseButton.setVisibility(View.INVISIBLE);
//        resetButton.setVisibility(View.INVISIBLE);
        clockgif.setVisibility(View.INVISIBLE);
        pausetxt.setVisibility(View.INVISIBLE);
        resettxt.setVisibility(View.INVISIBLE);
        continuetxt.setVisibility(View.INVISIBLE);

        startButton.setOnClickListener(view -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                handler.post(updateTimerThread);
                isRunning = true;

//                startButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
                clockgif.setVisibility(View.VISIBLE);

                pausetxt.setVisibility(View.VISIBLE);
                resettxt.setVisibility(View.VISIBLE);


                startButton.setActivated(false);
                pauseButton.setActivated(true);
                resetButton.setActivated(true);


            }
        });

        pauseButton.setOnClickListener(view -> {
            if (isRunning) {
                timeSwapBuff += timeInMilliseconds;
                handler.removeCallbacks(updateTimerThread);
                isRunning = false;

                startButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
                continuetxt.setVisibility(View.VISIBLE);
                pausetxt.setVisibility(View.INVISIBLE);
                resettxt.setVisibility(View.VISIBLE);
                startButton.setActivated(true);
                pauseButton.setActivated(false);
                resetButton.setActivated(true);
                clockgif.setVisibility(View.INVISIBLE);
            }
        });

        resetButton.setOnClickListener(view -> {
            timeSwapBuff = 0L;
            timeInMilliseconds = 0L;
            updateTime = 0L;
            isRunning = false;
            timerText.setText("00:00:00");
            handler.removeCallbacks(updateTimerThread);

//            startButton.setVisibility(View.VISIBLE);
//            pauseButton.setVisibility(View.GONE);
//            resetButton.setVisibility(View.GONE);
            clockgif.setVisibility(View.INVISIBLE);
            startButton.setActivated(true);
            pauseButton.setActivated(true);
            resetButton.setActivated(false);
            pausetxt.setVisibility(View.INVISIBLE);
            continuetxt.setVisibility(View.VISIBLE);
            resettxt.setVisibility(View.INVISIBLE);
        });


    }
}