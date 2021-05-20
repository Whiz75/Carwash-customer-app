package com.example.findcarwashapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.dialogs.LoadingDialogFragment;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //call methods here
        splashScreen();

    }

    private void splashScreen()
    {
        LoadingDialogFragment dialogFragment = new LoadingDialogFragment("loading...please wait");
        dialogFragment.show(getSupportFragmentManager().beginTransaction(), "LOADING");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }); thread.start();
    }
}