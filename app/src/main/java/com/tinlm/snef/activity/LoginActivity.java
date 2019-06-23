package com.tinlm.snef.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;

public class LoginActivity extends AppCompatActivity {

    CardView formLoginButton;
    ImageView appImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initAction();

    }

    // setting layout for screen
    private void init() {
        formLoginButton = findViewById(R.id.formLoginButton);
        appImage = findViewById(R.id.appImage);
    }

    private void initAction() {
        formLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
