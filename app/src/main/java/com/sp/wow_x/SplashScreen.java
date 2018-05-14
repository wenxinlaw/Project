package com.sp.wow_x;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {

                    mp=MediaPlayer.create(SplashScreen.this, R.raw.longexpected);
                    mp.start();
                    sleep(3000);
                } catch (InterruptedException e) {

                } finally {
                    Intent openStartingPoint = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        mp.release();
    }
}
