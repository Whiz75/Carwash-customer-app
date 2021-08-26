package com.example.findcarwashapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.dialogs.LoadingDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //call methods here
        splashScreen();
    }

    private void splashScreen() {
        /*LoadingDialogFragment dialogFragment = new LoadingDialogFragment("loading...please wait");
        dialogFragment.show(getSupportFragmentManager().beginTransaction(), "LOADING");*/

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent map = new Intent(getApplicationContext(),MapActivity.class);
                    startActivity(map);
                }else {
                    Intent intent = new Intent(getApplicationContext(),LoginSignupActivity.class);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); thread.start();
    }
}