package io.github.karan.mbus.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * A class representing the splash screen
 */
public class mBus_SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, mBus_SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
