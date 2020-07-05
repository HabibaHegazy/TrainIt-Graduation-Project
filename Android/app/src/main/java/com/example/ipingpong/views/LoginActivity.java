package com.example.ipingpong.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.LoginCallBack;
import com.example.ipingpong.controllers.UserController;
import com.example.ipingpong.shared.datasource.local.SharedPrefManager;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.AdminProfile.AdminActivity;
import com.example.ipingpong.views.ClubManagerProfile.ClubManagerActivity;
import com.example.ipingpong.views.CoachProfile.CoachActivity;
import com.example.ipingpong.views.PlayerProfile.PlayerActivity;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBtn;
    private TextView registerBtn, forgetPasswordBtn;
    private UserController userController;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        if(sharedPrefManager.checkLogin()){
            switchToActivity(sharedPrefManager.getUser());
        }

        userController = new UserController(this);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        forgetPasswordBtn = findViewById(R.id.forgot_password);
        registerBtn = findViewById(R.id.registerBtn);

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = emailET.getText().toString();
                String email = "habiba@gmail.com";            // coach
                //String email = "ibrahimFawzy@gmail.com";      // player
                //String email = "moustafa@gmail.com";          // admin
                //String email = "kareem_mohsen@gmail.com";       // club manager

                //String password = passwordET.getText().toString();
                String password = "123";

//                if (loginController.checkEmail(email) && loginController.checkPassword(password)) {
                   login(email, password);
//                } else {
//                    // error massage
//                }
            }
        });
    }

    private void login(final String email, final String password) {

        userController.login(email, password, new LoginCallBack() {
            @Override
            public void onLoginSuccess(User user) {

                finish();

                sharedPrefManager.login(user);

                // switch to another activity according to user type
                switchToActivity(user);
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void switchToActivity(User user){
        switch (user.getUserType()) {
            case COACH:
                Intent intent1 = new Intent(getApplicationContext(), CoachActivity.class);
                intent1.putExtra("User", (Serializable) user);
                startActivity(intent1);
                break;
            case PLAYER:
                Intent intent2 = new Intent(getApplicationContext(), PlayerActivity.class);
                intent2.putExtra("User", (Serializable) user);
                startActivity(intent2);
                break;
            case ClubManager:
                Intent intent3 = new Intent(getApplicationContext(), ClubManagerActivity.class);
                intent3.putExtra("User", (Serializable) user);
                startActivity(intent3);
                break;
            case Admin:
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                break;
        }
    }

}
