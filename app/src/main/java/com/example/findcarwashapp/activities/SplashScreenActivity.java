package com.example.findcarwashapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.dialogs.NetworkConnectivityDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        MaterialTextView textView = findViewById(R.id.app_text);

        //check network connectivity
        if (isNetworkAvailable()){
            textView.setText("");
            splashScreen();
        }else {
            // Toast.makeText(getApplicationContext(),"Please connect to the internet!!!!",Toast.LENGTH_LONG).show();
            textView.setText(R.string.network_error_connection_text);

            NetworkConnectivityDialogFragment networkConnectivity = new NetworkConnectivityDialogFragment();
            networkConnectivity.show(getSupportFragmentManager().beginTransaction(),"");
        }
    }

    private void splashScreen() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //check for both wifi and data connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}