package com.skeleton.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.model.MyCommonResponse;

/**
 * user information
 */
public class UserInformationActivity extends AppCompatActivity {
    private TextView tvFname, tvLname, tvAccess, tvId, tvDob, tvCreated, tvUpdated, tvCountry, tvPhone, tvUserImage, tvGender, tvAbout, tvlanguage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        init();
    }

    /**
     * initilizes views and variables
     */
    private void init() {
        MyCommonResponse myCommonResponse = (MyCommonResponse) getIntent().getSerializableExtra("response");

        tvFname = (TextView) findViewById(R.id.tv_fname);
        tvLname = (TextView) findViewById(R.id.tv_lname);
        tvAccess = (TextView) findViewById(R.id.tv_access);
        tvId = (TextView) findViewById(R.id.tv_id);
        tvDob = (TextView) findViewById(R.id.tv_dob);
        tvCreated = (TextView) findViewById(R.id.tv_created);
        tvUpdated = (TextView) findViewById(R.id.tv_updated);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvGender = (TextView) findViewById(R.id.tv_gender);
        tvAbout = (TextView) findViewById(R.id.tv_about);
        tvlanguage = (TextView) findViewById(R.id.tv_language);
        appendTextViews(myCommonResponse);

    }

    /**
     *
     * @param myCommonResponse response from server
     */
    private void appendTextViews(final MyCommonResponse myCommonResponse) {
        tvFname.append(myCommonResponse.getData().getUserDetails().getFirstName());
        tvLname.append(myCommonResponse.getData().getUserDetails().getLastName());
        tvAccess.append(myCommonResponse.getData().getAccessToken());
        tvId.append(myCommonResponse.getData().getUserDetails().getId());
        tvDob.append(myCommonResponse.getData().getUserDetails().getDob());
        tvCreated.append(myCommonResponse.getData().getUserDetails().getCreatedAt());
        tvUpdated.append(myCommonResponse.getData().getUserDetails().getUpdatedAt());
        tvPhone.append(myCommonResponse.getData().getUserDetails().getPhoneNo());
        tvGender.append(myCommonResponse.getData().getUserDetails().getGender());
        tvAbout.append(myCommonResponse.getData().getUserDetails().getAboutMe());
        tvlanguage.append(myCommonResponse.getData().getUserDetails().getLanguage());

    }
}
