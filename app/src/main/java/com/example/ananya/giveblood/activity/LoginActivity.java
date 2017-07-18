package com.example.ananya.giveblood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.ananya.giveblood.R;
import com.example.ananya.giveblood.application.GiveBloodApplication;
import com.example.ananya.giveblood.entities.UserEntity;
import com.example.ananya.giveblood.service.URLHelper;
import com.example.ananya.giveblood.service.handler.AlertDialogHandler;
import com.example.ananya.giveblood.service.handler.ResponseHandler;
import com.example.ananya.giveblood.service.impl.VolleyStringRequest;
import com.example.ananya.giveblood.util.Utility;
import com.example.ananya.giveblood.util.VolleyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button buttonLogin;
    private TextView buttonRegister;

    private String userNameFromEditText;
    private String passwordFromEditText;


    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize userEntity
        userEntity = new UserEntity();

        userName = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        buttonLogin = (Button) findViewById(R.id.bt_login);
        buttonRegister = (TextView) findViewById(R.id.bt_registration);

        //Login Click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClick();
            }
        });

        //Registration Click- Open Registration Screen
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    private void loginButtonClick() {
        //Get the value from UserName EditText
        userNameFromEditText = userName.getText().toString().trim();
        passwordFromEditText = password.getText().toString().trim();

        if (userNameFromEditText.equals(Utility.EMPTY_STRING) || passwordFromEditText.equals(Utility.EMPTY_STRING)) {
            // Show Error  message in alert dialog
            showErrorMessageInAlertDialogue();
            userName.requestFocus();
        } else {
            // Service call
            Utility.showProgressDialog(this);
            login(userNameFromEditText, new LoginHandler());
        }

    }

    // Service Call
    private void login(String email, ResponseHandler handler) {
        Map<String, Object> map = new HashMap<>();
        map.put(Utility.EMAIL, email);
        String url = URLHelper.getQeryEndpoint(Utility.USER_TABLE, map, null);
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.GET, url, handler, handler);
        VolleyUtils.getVolleyUtils(GiveBloodApplication.getContext()).addToRequestQueue(request);
    }

    // Error Message Alert
    private void showErrorMessageInAlertDialogue() {
        Utility.hideProgressDialog();
        Utility.showAlertDialog(this, getString(R.string.app_name), getString(R.string.login_empty_error_message), false, getString(R.string.ok_msg),
                null, new AlertDialogHandler() {
                    @Override
                    public void onPositiveButtonClicked() {
                    }

                    @Override
                    public void onNegativeButtonClicked() {
                    }

                    @Override
                    public void onMultiChoiceClicked(int position, boolean isChecked) {
                    }
                });
    }

    private void showAlertLoginFailure(String message) {
        Utility.hideProgressDialog();
        Utility.showAlertDialog(this, getString(R.string.app_name), message, false, getString(R.string.ok_msg), null,
                new AlertDialogHandler() {
                    @Override
                    public void onPositiveButtonClicked() {
                    }

                    @Override
                    public void onNegativeButtonClicked() {
                    }

                    @Override
                    public void onMultiChoiceClicked(int position, boolean isChecked) {
                    }
                });
    }

    class LoginHandler implements ResponseHandler {

        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError || error instanceof NoConnectionError) {
                Utility.showNetworkError(LoginActivity.this);
            } else if (error instanceof TimeoutError) {
                Utility.showRequestTimeOutError(LoginActivity.this);
            } else {
                Utility.showUnexpectedError(LoginActivity.this);
            }
        }

        @Override
        public void onResponse(String response) {
            if (response == null) {
                Utility.showUnexpectedError(LoginActivity.this);
            } else if (!response.equalsIgnoreCase("[]")) {
                String passwordValue = password.getText().toString().trim();
                Type listType = new TypeToken<ArrayList<UserEntity>>() {
                }.getType();
                List<UserEntity> entityList = new Gson().fromJson(response, listType);
                userEntity = entityList.get(0);
                if (userEntity.getPassword().equals(passwordValue)) {
                    // Successful Logged in, Open UserLanding Screen
                    Intent intent = new Intent(LoginActivity.this, UserLandingActivity.class);
                    intent.putExtra(Utility.USER_ENTITY,userEntity);
                    LoginActivity.this.startActivity(intent);
                    // Clear the fields
                    userName.setText("");
                    password.setText("");
                    Utility.hideProgressDialog();
                }
            } else {
                showAlertLoginFailure(getString(R.string.invalid_login_parameters));
            }
        }
    }
}

