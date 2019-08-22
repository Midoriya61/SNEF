package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.constain.ConstainServer;
import com.tinlm.snef.model.Customer;
import com.tinlm.snef.utilities.CustomerUtilities;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    CardView formLoginButton;
    ImageView appImage;
    TextView errLogin;


    EditText txtUsername , txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        String userIsLogn = getAccountLogin();
        if(userIsLogn != null) {
            changeScreen();
        }
    }
    // setting layout for screen
    private void init() {

        formLoginButton = findViewById(R.id.formLoginButton);
        appImage = findViewById(R.id.appImage);
        txtUsername =  findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        errLogin = findViewById(R.id.errLogin);


            formLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = true;
                    if(TextUtils.isEmpty(txtUsername.getText())){
                        txtUsername.setError("Bạn cần nhập tên");
                        checked =false;
                    }
                    if(TextUtils.isEmpty(txtPassword.getText())){
                        txtPassword.setError("Bạn cần nhập mật khẩu");
                        checked =false;
                    } else {
                        if( txtPassword.getText().length() < 6 || txtPassword.getText().length() > 30 ) {
                            txtPassword.setError("Mật khẩu phải có độ dài từ 6 đến 30 ký tự");
                            checked =false;
                        }
                    }
                    if( checked ) {
                    CustomerUtilities customerUtilities = new CustomerUtilities();
                    Customer customer = customerUtilities.login(txtUsername.getText().toString(), txtPassword.getText().toString());
                    if(customer == null) {
                        errLogin.setText(getResources().getString(R.string.msg_login_fail));
                        errLogin.setTextColor(getResources().getColor(R.color.redApp));
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(ConstainApp.ACCOUNTID, customer.getAccountId());
                        editor.putString(ConstainApp.USERNAME, customer.getUsername());
                        editor.putString(ConstainApp.PASSWORD, customer.getPassword());
                        editor.putString(ConstainApp.FIRSTNAME, customer.getFirstName());
                        editor.putString(ConstainApp.LASTNAME, customer.getLastName());
                        editor.putString(ConstainApp.EMAIL, customer.getEmail());
                        editor.putString(ConstainApp.AVATAR, customer.getAvatar());
                        editor.putString(ConstainApp.PHONE, customer.getPhone());
                        if( customer.getGender() == 0 ) {
                            editor.putString(ConstainApp.GENDER, "Nam");
                        } else {
                            editor.putString(ConstainApp.GENDER, "Nữ");
                        }
                        editor.apply();
                        changeScreen();
                    }
                    } else {
                        errLogin.setText("");
                        errLogin.setTextColor(getResources().getColor(R.color.colorMain));
                    }
                }
            });

    }


    //TinLM load get account is login
    private String getAccountLogin() {
        String username;
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
        username = sharedPreferences.getString(ConstainApp.USERNAME,null);
        return username;
    }


    public void clickToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void changeScreen() {

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
