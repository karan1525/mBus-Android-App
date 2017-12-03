package io.github.karan.mbus.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import io.github.karan.mbus.R;

public class mBus_HelpActivity extends AppCompatActivity {

    EditText faq1;
    EditText faq2;
    EditText faq3;
    EditText faq4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__help);

        faq1 = findViewById(R.id.faq1);
        faq2 = findViewById(R.id.faq2);
        faq3 = findViewById(R.id.faq3);
        faq4 = findViewById(R.id.faq4);

        faq1.setClickable(false);
        faq1.setFocusable(false);
        faq2.setClickable(false);
        faq2.setFocusable(false);
        faq3.setClickable(false);
        faq3.setFocusable(false);
        faq4.setClickable(false);
        faq4.setFocusable(false);
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
