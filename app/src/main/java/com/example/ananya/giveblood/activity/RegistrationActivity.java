package com.example.ananya.giveblood.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

/**
 * Created by Ananya on 31-10-2016.
 */

public class RegistrationActivity extends AppCompatActivity implements TextWatcher {

    private EditText name, email, password, phone, location;
    private CheckBox isDonar;
    private Button submit;
    private Spinner bloodGroupnnerSpinner;

    private String nameFromEditText, emailFromEditText, passwordFromEditText, phoneFromEitText,
            locationFromEditText, selectedBloodGroup;
    private boolean isCheckBoxChecked;

    private UserEntity userEntity;

    private UserEntity userEntityFromBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Spinner Drop down elements
        List<String> bloodGroup = new ArrayList<String>();
        bloodGroup.add("A+ ve");
        bloodGroup.add("A- ve");
        bloodGroup.add("B+ ve");
        bloodGroup.add("B- ve");
        bloodGroup.add("O+ ve");
        bloodGroup.add("O- ve");
        bloodGroup.add("AB+ ve");
        bloodGroup.add("AB- ve");

        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        phone = (EditText) findViewById(R.id.et_phone);
        location = (EditText) findViewById(R.id.et_location);

        isDonar = (CheckBox) findViewById(R.id.cb_donar);

        submit = (Button) findViewById(R.id.bt_submit);

        bloodGroupnnerSpinner = (Spinner) findViewById(R.id.sn_blood_group);

        //Set items to spinner
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroup);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupnnerSpinner.setAdapter(stringArrayAdapter);

        //spinner selection
        bloodGroupnnerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBloodGroup = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CheckBox Selection
        isDonar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {
                    isCheckBoxChecked = isChecked;
                } else {
                    isCheckBoxChecked = false;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If Edit Mode
                if (userEntityFromBundle != null) {
                    //
                }
                // Normal mode
                else {
                    emailFromEditText = email.getText().toString().trim();
                    // Save the Details in the Kinvey, Check weather user already exists
                    if (Utility.isValidEmail(emailFromEditText)) {
                        saveUserDetails();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_email_message), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // Add Title
        setTitle(getString(R.string.registration_title));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // By default, All button is disabled, and the color will be grey,
        // Once user enters all the fields, then enable it
        addListeners();

        // Get UserEntity from previous class
        userEntityFromBundle = (UserEntity) getIntent().getSerializableExtra(Utility.USER_ENTITY);

        // Prepopulate the values
        if (userEntityFromBundle != null) {
            name.setText(userEntityFromBundle.getUserName());
            email.setText(userEntityFromBundle.getEmail());
            password.setText(userEntityFromBundle.getPassword());
            phone.setText(userEntityFromBundle.getPhoneNumber());
            location.setText(userEntityFromBundle.getLocation());

            bloodGroup.set(0, userEntityFromBundle.getBloodGroup());
            if (userEntityFromBundle.getIsDonar().equals("true")) {
                isDonar.setChecked(true);
            } else {
                isDonar.setChecked(false);
            }
        }
    }

    private void addListeners() {
        name.addTextChangedListener(this);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        phone.addTextChangedListener(this);
        location.addTextChangedListener(this);
    }

    // Store value in the db
    private void saveUserDetails() {

        //Get selected values fro editText/CheckBox/Spinner
        nameFromEditText = name.getText().toString().trim();
        emailFromEditText = email.getText().toString().trim();
        passwordFromEditText = password.getText().toString().trim();
        phoneFromEitText = phone.getText().toString().trim();
        locationFromEditText = location.getText().toString().trim();

        userEntity = new UserEntity();

        userEntity.setUserName(nameFromEditText);
        userEntity.setEmail(emailFromEditText);
        userEntity.setPassword(passwordFromEditText);
        userEntity.setPhoneNumber(phoneFromEitText);
        userEntity.setLocation(locationFromEditText);
        userEntity.setIsDonar(String.valueOf(isCheckBoxChecked));
        userEntity.setBloodGroup(selectedBloodGroup);

        Utility.hideKeyBoard(this);
        startAddingUserInitiative();
    }

    private void startAddingUserInitiative() {
        Utility.showProgressDialog(this);

        checkIfUserAlreadyPresentInDB();
    }

    private void checkIfUserAlreadyPresentInDB() {
        Map<String, Object> map = new HashMap<>();
        map.put(Utility.EMAIL, userEntity.getEmail());
        findUser(userEntity.getEmail(), new FindUserHandler());
    }

    // Alert dialogue if User is already registered
    private void showDeviceAlreadyRegistered() {
        Utility.hideProgressDialog();
        Utility.showAlertDialog(this, getString(R.string.app_name), getString(R.string.user_already_exists), false, getString(R.string.ok_msg), null, new AlertDialogHandler() {
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

    // Alert dialogue if User added successfully
    private void deviceAddedSuccessfully() {
        Utility.hideProgressDialog();

        Utility.showAlertDialog(this, getString(R.string.app_name), getString(R.string.succes_user_added), false, getString(R.string.ok_msg), null, new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
                // Come back to Login Activity
                finish();
            }

            @Override
            public void onNegativeButtonClicked() {

            }

            @Override
            public void onMultiChoiceClicked(int position, boolean isChecked) {

            }
        });
    }

    // Method to save user Details in DB
    private void saveUser(UserEntity userEntity, SaveUserHandler saveUserHandler) {
        int method;
        if (userEntity.getObjectId() == null || userEntity.getObjectId().isEmpty()) {
            method = Request.Method.POST;
        } else {
            method = Request.Method.PUT;
        }
        VolleyStringRequest request = new VolleyStringRequest(method, URLHelper.getAPIEndpoint(Utility.USER_TABLE, userEntity.getObjectId(), null), saveUserHandler, saveUserHandler);
        request.setBody(new Gson().toJson(userEntity));
        request.addHeaders(Utility.CONTENT_TYPE, Utility.APPLICATION_JSON);
        VolleyUtils.getVolleyUtils(GiveBloodApplication.getContext()).addToRequestQueue(request);
    }

    // Method to check weather the user already exists in the DB
    private void findUser(String userEmail, FindUserHandler userHandler) {
        Map<String, Object> map = new HashMap<>();
        map.put(Utility.EMAIL, userEmail);
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.GET,
                URLHelper.getQeryEndpoint(Utility.USER_TABLE, map, null), userHandler, userHandler);
        VolleyUtils.getVolleyUtils(GiveBloodApplication.getContext()).addToRequestQueue(request);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        //Get selected values fro editText/CheckBox/Spinner
        nameFromEditText = name.getText().toString().trim();
        emailFromEditText = email.getText().toString().trim();
        passwordFromEditText = password.getText().toString().trim();
        phoneFromEitText = phone.getText().toString().trim();
        locationFromEditText = location.getText().toString().trim();

        if (Utility.emptyAndNullCheck(nameFromEditText) && Utility.emptyAndNullCheck(emailFromEditText)
                && Utility.emptyAndNullCheck(passwordFromEditText) && Utility.emptyAndNullCheck(phoneFromEitText) && Utility.emptyAndNullCheck(locationFromEditText)) {
            // Enable the submit Button
            submit.setEnabled(true);
            submit.setTextColor(Color.BLACK);
        } else {
            submit.setEnabled(false);
            submit.setTextColor(Color.GRAY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    // Save User handler
    class SaveUserHandler implements ResponseHandler {

        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError || error instanceof NoConnectionError) {
                Utility.showNetworkError(RegistrationActivity.this);
            } else if (error instanceof TimeoutError) {
                Utility.showRequestTimeOutError(RegistrationActivity.this);
            } else {
                Utility.showUnexpectedError(RegistrationActivity.this);
            }
        }

        @Override
        public void onResponse(String response) {
            if (response != null) {
                UserEntity temp = new Gson().fromJson(response, UserEntity.class);
                if (temp.getObjectId() != null) {
                    deviceAddedSuccessfully();
                }
            } else {
                Utility.showUnexpectedError(RegistrationActivity.this);
            }
        }
    }

    // Find User handler
    class FindUserHandler implements ResponseHandler {

        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError || error instanceof NoConnectionError) {
                Utility.showNetworkError(RegistrationActivity.this);
            } else if (error instanceof TimeoutError) {
                Utility.showRequestTimeOutError(RegistrationActivity.this);
            } else {
                Utility.showUnexpectedError(RegistrationActivity.this);
            }
        }

        @Override
        public void onResponse(String response) {
            if (response != null && response.equalsIgnoreCase("[]")) {
                saveUser(userEntity, new SaveUserHandler());
            } else {
                Type listType = new TypeToken<ArrayList<UserEntity>>() {
                }.getType();
                List<UserEntity> tempList = new Gson().fromJson(response, listType);
                UserEntity temp = tempList.get(0);
                if (!temp.getEmail().equals(userEntity.getEmail())) {
                    saveUser(userEntity, new SaveUserHandler());
                } else {
                    // User is already Registered
                    showDeviceAlreadyRegistered();
                }
            }
        }
    }
}
