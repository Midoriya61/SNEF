package com.tinlm.snef.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }


    }

    // setting layout for screen
    private void init() {

        formLoginButton = findViewById(R.id.formLoginButton);
        appImage = findViewById(R.id.appImage);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        errLogin = findViewById(R.id.errLogin);

        formLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerUtilities customerUtilities = new CustomerUtilities();
                Customer customer = customerUtilities.login(txtUsername.getText().toString(), txtPassword.getText().toString());
                if(customer == null) {
                    errLogin.setText(getResources().getString(R.string.msg_login_fail));
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(ConstainApp.login_Prefer, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ConstainApp.USERNAME, customer.getUsername());
                    editor.putString(ConstainApp.PASSWORD, customer.getPassword());
                    editor.putString(ConstainApp.FIRSTNAME, customer.getFirstName());
                    editor.putString(ConstainApp.LASTNAME, customer.getLastName());
                    editor.putString(ConstainApp.EMAIL, customer.getEmail());
                    editor.putString(ConstainApp.AVATAR, customer.getAvatar());
                    editor.putString(ConstainApp.PHONE, customer.getPhone());
                    editor.putInt(ConstainApp.ACCOUNTID, customer.getAccountId());
                    if( customer.getGender() == 0 ) {
                        editor.putString(ConstainApp.GENDER, "Nam");
                    } else {
                        editor.putString(ConstainApp.GENDER, "Nữ");
                    }

                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

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
}
