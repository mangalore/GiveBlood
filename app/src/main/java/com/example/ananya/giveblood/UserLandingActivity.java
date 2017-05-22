package com.example.ananya.giveblood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ananya on 31-10-2016.
 */

public class UserLandingActivity extends AppCompatActivity {

    private TextView landingName,landingLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        landingName = (TextView)findViewById(R.id.tv_landing_name);
        landingLocation = (TextView)findViewById(R.id.tv_landing_location);
    }
}
