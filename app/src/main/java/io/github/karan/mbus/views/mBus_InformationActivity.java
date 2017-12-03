package io.github.karan.mbus.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MultiAutoCompleteTextView;

import io.github.karan.mbus.R;

public class mBus_InformationActivity extends AppCompatActivity {

    MultiAutoCompleteTextView aboutApp;
    MultiAutoCompleteTextView contactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__information);

        aboutApp = findViewById(R.id.about_the_app);
        contactUs = findViewById(R.id.contact_us);

        aboutApp.setClickable(false);
        aboutApp.setFocusable(false);
        contactUs.setFocusable(false);
        contactUs.setClickable(false);
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
