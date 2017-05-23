package com.skeleton.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.activity.UserInformationActivity;
import com.skeleton.model.MyCommonResponse;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.imagepicker.ImageChooser;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends BaseFragment {
    private MaterialEditText etFname, etLname, etPhone, etEmail, etDob, etPassword, etCpassword;
    private Spinner spOrientation;
    private CircleImageView ciProfileImage;
    private RadioGroup rgSexRadioGroup;
    private RadioButton rbMale, rbFemale;
    private CheckBox checkTerms;
    private Button btSignup;
    private ImageChooser imageChooser = new ImageChooser.Builder(SignupFragment.this).build();
    private boolean isImageSet = false;
    private File imageFile;
    private int mGender;
    private String mDateOfBirth;


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        imageChooser.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, null);
        init(v);
        chooseImage();
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (validateData()) {
                    uploadData();
                }
            }
        });

        return v;
    }

    /**
     * initialization
     *
     * @param v layout view
     */

    private void init(final View v) {

        etFname = (MaterialEditText) v.findViewById(R.id.et_f_name);
        etLname = (MaterialEditText) v.findViewById(R.id.et_l_name);
        etPhone = (MaterialEditText) v.findViewById(R.id.et_phone);
        etEmail = (MaterialEditText) v.findViewById(R.id.et_email);
        etDob = (MaterialEditText) v.findViewById(R.id.et_dob);
        etPassword = (MaterialEditText) v.findViewById(R.id.et_password);
        etCpassword = (MaterialEditText) v.findViewById(R.id.et_c_password);
        spOrientation = (Spinner) v.findViewById(R.id.sp_orientation);
        ciProfileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        rgSexRadioGroup = (RadioGroup) v.findViewById(R.id.sex_group);
        rbMale = (RadioButton) v.findViewById(R.id.radio_male);
        rbFemale = (RadioButton) v.findViewById(R.id.radio_female);
        checkTerms = (CheckBox) v.findViewById(R.id.cb_terms);
        btSignup = (Button) v.findViewById(R.id.bt_sign_up);
        rgSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup group, @IdRes final int checkedId) {
                if (checkedId == R.id.radio_male) {
                    mGender = 0;
                } else {
                    mGender = 1;
                }
            }
        });
        Log.d("gender", Integer.toString(mGender));
    }

    /**
     * choose image
     */
    private void chooseImage() {
        ciProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
                    @Override
                    public void loadImage(final List<ChosenImage> list) {
                        ChosenImage image = list.get(0);
                        imageFile = new File(String.valueOf(image.getQueryUri()));
                        Glide
                                .with(SignupFragment.this)
                                .load(image.getQueryUri())
                                .centerCrop()
                                .into(ciProfileImage);
                        isImageSet = true;

                    }

                    @Override
                    public void croppedImage(final File mCroppedImage) {

                    }
                });
                imageChooser.onActivityResult(1, 1, null);

            }
        });

    }


    /**
     * @return valid fields
     */

    private boolean validateData() {
        if (!ValidateEditText.checkName(etFname, true)) {
            return false;
        } else if (!ValidateEditText.checkName(etLname, false)) {
            return false;
        } else if (!ValidateEditText.checkPhoneNumber(etPhone)) {
            return false;
        } else if (!ValidateEditText.checkEmail(etEmail)) {
            return false;
        } else if (!ValidateEditText.checkPassword(etPassword, false)) {
            return false;
        } else if (!ValidateEditText.checkPassword(etCpassword, true)) {
            return false;
        } else if (!checkDOB(etDob)) {
            return false;
        } else if (!ValidateEditText.comparePassword(etPassword, etCpassword)) {
            Toast.makeText(getContext(), R.string.error_password_mismatch, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isImageSet) {
            Toast.makeText(getActivity(), "Please select Image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkTerms.isChecked()) {
            Toast.makeText(getActivity(), "Please select Terms and Cond.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * upload data to server
     */
    private void uploadData() {
        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add("firstName", etFname.getText().toString().trim())
                .add("lastName", etLname.getText().toString().trim())
                .add("dob", etDob.getText().toString().trim())
                .add("countryCode", "+91")
                .add("phoneNo", etPhone.getText().toString().trim())
                .add("email", etEmail.getText().toString().trim())
                .add("password", etPassword.getText().toString().trim())
                .add("language", "EN")
                .add("deviceType", "ANDROID")
                .add("deviceToken", "tok")
                .add("appVersion", "v")
                .add("gender", mGender)
                .add("orientation", "Straight")
                .add("profilePic", imageFile).build().getMap();
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.register(multipartParams).enqueue(new ResponseResolver<MyCommonResponse>(getActivity(), true, true) {
            @Override
            public void success(final MyCommonResponse myCommonResponse) {
                Log.d(TAG, "success: " + myCommonResponse.getStatusCode());
                Toast.makeText(getContext(), myCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                if ("200".equals(myCommonResponse.getStatusCode().toString())) {
                    clearEditText(etFname, etLname, etPhone, etDob,
                            etPassword, etCpassword, etEmail);
                    Intent intent = new Intent(getActivity(), UserInformationActivity.class);
                    intent.putExtra("response", myCommonResponse);
                    startActivity(intent);

                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d(TAG, "failure: Status " + error.getStatusCode());
                Log.d(TAG, "failure: Message" + error.getMessage());
            }
        });

    }
    /**
     * Clear the string in the editext
     *
     * @param editText : multiple edittexts to be cleared
     */
    public static void clearEditText(final EditText... editText) {
        for (EditText et : editText) {
            et.setText("");
        }
    }

    /**
     * @param editText DOB EDITTEXT
     * @return DOB FORMAT
     */

    private boolean checkDOB(final EditText editText) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = editText.getText().toString();
        try {
            Date date = df.parse(s);
            mDateOfBirth = s;
            return true;

        } catch (ParseException e) {
            editText.setError(getString(R.string.invalid_date_error));
            return false;
        }
    }
}

