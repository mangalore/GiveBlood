package com.example.ananya.giveblood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ananya.giveblood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ananya on 31-10-2016.
 */

public class RegistrationActivity extends AppCompatActivity {

    private EditText name,userName,password,phone,location,email;
    private CheckBox isDonar;
    private Button submit;
    private Spinner bloodGroupnnerSpinner;

    private String nameFromEditText,userNameFromEditText,passwordFromEditText,phoneFromEitText,
                    locationFromEditText,emailFromEditText,selectedBloodGroup;
    private boolean isCheckBoxChecked;

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

        name = (EditText)findViewById(R.id.et_name);
        userName = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);
        phone = (EditText)findViewById(R.id.et_phone);
        phone = (EditText)findViewById(R.id.et_phone);
        location = (EditText)findViewById(R.id.et_location);
        email = (EditText)findViewById(R.id.et_email);

        isDonar = (CheckBox)findViewById(R.id.cb_donar);

        submit = (Button)findViewById(R.id.bt_submit);

        bloodGroupnnerSpinner = (Spinner)findViewById(R.id.sn_blood_group);

        //Set items to spinner
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroup);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupnnerSpinner.setAdapter(stringArrayAdapter);

        //spinner selection
        bloodGroupnnerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Submit Click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //CheckBox Selection
        isDonar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        //Get selected values fro editText/CheckBox/Spinner

//        nameFromEditText = name.getText().toString();
//        userNameFromEditText = userName.getText().toString();
//        passwordFromEditText = password.getText().toString();
//        phoneFromEitText = phone.getText().toString();
//        locationFromEditText = location.getText().toString();
//        emailFromEditText = email.getText().toString();

    }
}
