package com.tinlm.snef.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;

import static android.view.View.GONE;

public class EditAccountActivity extends AppCompatActivity {

    private Dialog dlChangePwd;
    private EditText edtPhone, edtEmail;
    private LinearLayout gender;
    private SharedPreferences sharedPreferences;
    private ImageView imgAvatar;
    private TextView txtGender, txtUsername, txtFirstName, txtLastName, txtChange, txtChangePassword, txtDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        init();
    }

    private void init() {
        txtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        gender = findViewById(R.id.gender);
        imgAvatar = findViewById(R.id.imgAvatar);
        txtGender = findViewById(R.id.txtGender);
        txtLastName = findViewById(R.id.txtLastName);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtChange = findViewById(R.id.txtChange);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        txtDone = findViewById(R.id.txtDone);


        sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
        txtUsername.setText(sharedPreferences.getString(ConstainApp.USERNAME, null));
        txtLastName.setText(sharedPreferences.getString(ConstainApp.LASTNAME, null));
        txtFirstName.setText(sharedPreferences.getString(ConstainApp.FIRSTNAME, null));
        Picasso.get().load(sharedPreferences.getString(ConstainApp.AVATAR, null)).resize(300, 350).into(imgAvatar);
        edtPhone.setEnabled(false);
        edtEmail.setEnabled(false);
        txtDone.setVisibility(GONE);
        edtPhone.setText(sharedPreferences.getString(ConstainApp.PHONE, "Chưa có"));
        edtEmail.setText(sharedPreferences.getString(ConstainApp.EMAIL, "Chưa có"));
        txtGender.setText(sharedPreferences.getString(ConstainApp.GENDER, "Giới tính thứ 3"));
    }

    public void clickToEdit(View view) {
        edtPhone.setEnabled(true);
        edtEmail.setEnabled(true);
        txtDone.setVisibility(View.VISIBLE);
        txtChange.setVisibility(View.GONE);

    }

    public void clickToConfirm(View view) {
        edtPhone.setEnabled(false);
        edtEmail.setEnabled(false);
        txtDone.setVisibility(View.GONE);
        txtChange.setVisibility(View.VISIBLE);
    }

    public void clickToChangePassword(View view) {
        dlChangePwd = new Dialog(this);
        Button btnCancel, btnChange;

        dlChangePwd.setContentView(R.layout.dialog_editpassword);
        btnCancel = dlChangePwd.findViewById(R.id.btnCancel);
        btnChange = dlChangePwd.findViewById(R.id.btnChange);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlChangePwd.dismiss();
            }
        });
        dlChangePwd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlChangePwd.show();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
