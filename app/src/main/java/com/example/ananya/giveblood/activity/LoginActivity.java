package com.example.ananya.giveblood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ananya.giveblood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button buttonLogin;
    private TextView buttonRegister;

    private String userNameFromEditText;
    private String passwordFromEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        buttonLogin = (Button) findViewById(R.id.bt_login);
        buttonRegister = (TextView) findViewById(R.id.bt_registration);

        //Login Click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserLandingActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        //Registration Click
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        //Get the value from UserName EditText
        userNameFromEditText = userName.getText().toString();
        passwordFromEditText = password.getText().toString();

    }

}

