package com.example.ananya.giveblood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ananya.giveblood.R;
import com.example.ananya.giveblood.entities.UserEntity;
import com.example.ananya.giveblood.util.Utility;

/**
 * Created by Ananya on 31-10-2016.
 */

public class UserLandingActivity extends AppCompatActivity {

    private TextView loggedInUserName, loggedInUserLocation,
            loggedInUserContactNumber, loggedInUserEmail;
    private UserEntity userEntity;
    private Button editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        loggedInUserName = (TextView) findViewById(R.id.tv_landing_name);
        loggedInUserLocation = (TextView) findViewById(R.id.tv_landing_location);
        loggedInUserContactNumber = (TextView) findViewById(R.id.tv_contact);
        loggedInUserEmail = (TextView) findViewById(R.id.tv_email);
        editButton = (Button)findViewById(R.id.bt_edit);

        // Add Title
        setTitle(getString(R.string.user_profile));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get UserEntity from previous class
        userEntity = (UserEntity) getIntent().getSerializableExtra(Utility.USER_ENTITY);

        setDetails();

        // Edit Button Click - Open the Registration Screen with prepopulating the value
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Successful Logged in, Open UserLanding Screen
                Intent intent = new Intent(UserLandingActivity.this, RegistrationActivity.class);
                intent.putExtra(Utility.USER_ENTITY,userEntity);
                UserLandingActivity.this.startActivity(intent);
            }
        });
    }

    //Show Details In the UI
    private void setDetails() {
        loggedInUserName.setText(userEntity.getUserName());
        loggedInUserLocation.setText(userEntity.getLocation());
        loggedInUserContactNumber.setText(userEntity.getPhoneNumber());
        loggedInUserEmail.setText(userEntity.getEmail());
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
}
