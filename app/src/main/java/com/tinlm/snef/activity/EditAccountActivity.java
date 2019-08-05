package com.tinlm.snef.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;

public class EditAccountActivity extends AppCompatActivity {

    private EditText edtUsername, edtPhone, edtEmail;
    private LinearLayout gender;
    private SharedPreferences sharedPreferences;
    private ImageView imgAvatar;
    private TextView txtGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        init();
    }

    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        gender = findViewById(R.id.gender);
        imgAvatar = findViewById(R.id.imgAvatar);
        txtGender = findViewById(R.id.txtGender);
        sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
        edtUsername.setText(sharedPreferences.getString(ConstainApp.LASTNAME, null) + " " + sharedPreferences.getString(ConstainApp.FIRSTNAME, null));
        Picasso.get().load(sharedPreferences.getString(ConstainApp.AVATAR, null)).resize(300,350).into(imgAvatar);
        edtPhone.setText(sharedPreferences.getString(ConstainApp.PHONE,""));
        edtEmail.setText(sharedPreferences.getString(ConstainApp.EMAIL,""));
        txtGender.setText(sharedPreferences.getString(ConstainApp.GENDER, "Giới tính thứ 3"));
    }
}
