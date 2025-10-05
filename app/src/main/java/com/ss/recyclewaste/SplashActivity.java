package com.ss.recyclewaste;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView appName;
    private ProgressBar progressBar, retryProgress;

    private static final int SPLASH_DELAY = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        animateViews();

        checkNetworkAndProceed();
    }

    private void initViews() {
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        progressBar = findViewById(R.id.progressBar);
        retryProgress = findViewById(R.id.retryProgress);

        // Initially hide retryProgress
        retryProgress.setVisibility(View.GONE);
    }

    private void animateViews() {
        Animation scaleFadeIn = AnimationUtils.loadAnimation(this, R.anim.scale_up_fade_in);
        logo.startAnimation(scaleFadeIn);

        Animation slideUpFadeIn = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in);
        appName.startAnimation(slideUpFadeIn);
    }

    private void checkNetworkAndProceed() {
        if (isNetworkAvailable()) {
            retryProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                PrefManager prefManager = new PrefManager(SplashActivity.this);
                Intent intent;
                if (prefManager.isLoggedIn()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }, SPLASH_DELAY);
        } else {
            progressBar.setVisibility(View.GONE);
            retryProgress.setVisibility(View.VISIBLE);
            animateRetryProgress();
            showToast("No internet connection. Tap to retry.");

            retryProgress.setOnClickListener(v -> {
                animateRetryProgress();
                checkNetworkAndProceed();
            });
        }
    }

    private void animateRetryProgress() {
        Animation fadeInScale = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale);
        retryProgress.startAnimation(fadeInScale);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        retryProgress.startAnimation(rotate);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
