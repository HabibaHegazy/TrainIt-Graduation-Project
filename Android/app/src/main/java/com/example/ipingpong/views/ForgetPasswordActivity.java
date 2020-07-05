package com.example.ipingpong.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.datasource.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static View view;
    private EditText emailEditText;
    private TextView submitTextViewBtn, backTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();
        setListeners();
    }

    private void initViews() {
        //emailEditText = view.findViewById(R.id.registered_emailid);
        //submitTextViewBtn = view.findViewById(R.id.forgot_button);
        //backTextViewBtn = view.findViewById(R.id.backToLoginBtn);
    }

    private void setListeners() {
        //backTextViewBtn.setOnClickListener(this);
        //submitTextViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:
                //((LoginActivity) getActivity()).replaceFragment(new LoginFragment(), Constants.Login_Fragment, true);
                // Go to login Activity
                break;

            case R.id.forgot_button:
                submitButtonTask();
                break;

        }
    }

    private void submitButtonTask() {
        String email = emailEditText.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Constants.regEx);

        // Match the pattern
        Matcher matcher = p.matcher(email);

        // First check if email id is not null else show error toast
        if (email.equals("") || email.length() == 0) {
            //new CustomToast().Show_Toast(getActivity(), view, "Please enter your Email Id.");

            // Check if email id is valid or not
        }else if (!matcher.find()){
            //new CustomToast().Show_Toast(getActivity(), view, "the Email is Invalid.");
        }
        else{
            // Else submit email id and fetch passwod or do your stuff
        }
    }
}
