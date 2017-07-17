package com.example.ananya.giveblood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ananya.giveblood.R;

/**
 * Created by Ananya on 31-10-2016.
 */

public class UserLandingActivity extends AppCompatActivity {

    private TextView landingName, landingLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        landingName = (TextView) findViewById(R.id.tv_landing_name);
        landingLocation = (TextView) findViewById(R.id.tv_landing_location);

        // Add Title
        setTitle(getString(R.string.user_profile));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
