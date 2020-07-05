package com.example.ipingpong.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.AddressController;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectAllAddresses;
import com.example.ipingpong.controllers.PlayerController;
import com.example.ipingpong.controllers.UserController;
import com.example.ipingpong.shared.entities.Address;
import com.example.ipingpong.shared.entities.UserType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private EditText firstNameET, lastNameET, phoneNumberET, emailET, passwordET, confirmPasswordET;
    private Spinner addressSpinnerCountry, addressSpinnerCity, addressSpinnerPlace;
    private Button registrationBtn, birthdateCalenderBtn;
    private RadioGroup genderRadioGroup, playedHandRadioGroup;

    private UserController userController;
    private PlayerController playerController;
    public AddressController addressController;
    public ArrayAdapter<Address> adapter;

    public HashMap<String, String> userHashmap = new HashMap<>();
    private HashMap<String, String> playerHashmap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        playerController = new PlayerController(getApplicationContext());
        userController = new UserController(getApplicationContext());
        addressController = new AddressController(getApplicationContext());

        initialize();
        setInputFilters();
        setListener();
    }

    private void initialize(){
        firstNameET = findViewById(R.id.firstNameEditText);
        lastNameET = findViewById(R.id.lastNameEditText);
        phoneNumberET = findViewById(R.id.phoneNumberEditText);
        emailET = findViewById(R.id.emailProfileEditText);
        passwordET = findViewById(R.id.passwordProfileEditText);
        confirmPasswordET = findViewById(R.id.confirmpasswordProfileEditText);
        birthdateCalenderBtn = findViewById(R.id.birthdateProfileBtn);
        registrationBtn = findViewById(R.id.registrationBtn);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        playedHandRadioGroup = findViewById(R.id.playedHandRadioGroup);
        addressSpinnerCountry = findViewById(R.id.addressSpinnerCountry);
        addressSpinnerCity = findViewById(R.id.addressSpinnerCity);
        addressSpinnerPlace = findViewById(R.id.addressSpinnerPlace);
    }

    private  void setInputFilters(){
        InputFilter filtertxt = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        firstNameET.setFilters(new InputFilter[]{filtertxt});
        lastNameET.setFilters(new InputFilter[]{filtertxt});
    }

    private void setListener(){

        getAdderesses(0, 1);

        birthdateCalenderBtn.setOnClickListener(this);
        registrationBtn.setOnClickListener(this);

        addressSpinnerCountry.setOnItemSelectedListener(this);
        addressSpinnerCity.setOnItemSelectedListener(this);
        addressSpinnerPlace.setOnItemSelectedListener(this);


        validateChanges();
    }

    private void validateChanges(){

        userHashmap.put("gender", "Male");
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radioFemaleProfile:
                        userHashmap.put("gender", "Female");
                        break;
                    case R.id.radioMaleProfile:
                        userHashmap.put("gender", "Male");
                        break;
                }
            }
        });

        playerHashmap.put("handUsed", "left");
        playedHandRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId){
                    case R.id.radioLeftHnad:
                        playerHashmap.put("handUsed", "left");
                        break;
                    case R.id.radioRightHand:
                        playerHashmap.put("handUsed", "right");
                        break;
                }
            }
        });

        firstNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userHashmap.put("firstName", firstNameET.getText().toString());
            }
        });

        lastNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userHashmap.put("lastName", lastNameET.getText().toString());
            }
        });

        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userHashmap.put("email", emailET.getText().toString());
            }
        });

        phoneNumberET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userHashmap.put("phoneNumber", phoneNumberET.getText().toString());
            }
        });

        confirmPasswordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if(passwordET.getText().equals(confirmPasswordET.getText())){
                    userHashmap.put("password", phoneNumberET.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Password Does not Match", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registrationBtn:
                registerPlayer();
                break;
            case R.id.birthdateProfileBtn:
                showDatePickerDialog(this);
                break;
        }
    }

    private void registerPlayer(){
        userHashmap.put("birthdate", birthdateCalenderBtn.getText().toString());
        userHashmap.put("usertypeID", String.valueOf(UserType.PLAYER.getValue()));

        if(userHashmap.size() == 8){
            userController.addUser(userHashmap, new InsertUserCallBack() {
                @Override
                public void onSuccess(String success, int userID) {
                    Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
                    addProfileData(userID);
                }
                @Override
                public void onFailure(String reason) {
                    Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Please Fill all Fields", Toast.LENGTH_LONG).show();
        }
    }

    private void addProfileData(int userID) {

        playerController.addPlayer(playerHashmap, userID, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        birthdateCalenderBtn.setText(year + "-" + (month+1) + "-" + day);
    }

    private void showDatePickerDialog (DatePickerDialog.OnDateSetListener listener) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), listener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        switch (spinner.getId()){
            case R.id.addressSpinnerCountry:

                getAdderesses(((Address) parent.getSelectedItem()).getId(), 2);
                addressSpinnerCity.setVisibility(View.VISIBLE);
                break;

            case R.id.addressSpinnerCity:
                getAdderesses(((Address) parent.getSelectedItem()).getId(), 3);
                addressSpinnerPlace.setVisibility(View.VISIBLE);
                break;

            case R.id.addressSpinnerPlace:
                userHashmap.put("addressID", String.valueOf(((Address) parent.getSelectedItem()).getId()));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void getAdderesses(final int parentID, final int spinnerNumber){

        addressController.getAllAddresses(parentID, new SelectAllAddresses() {
            @Override
            public void onSuccess(ArrayList<Address> addressesList) {

                adapter = new ArrayAdapter<>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, addressesList);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                switch (spinnerNumber){
                    case 1:
                        addressSpinnerCountry.setAdapter(adapter);
                        break;
                    case 2:
                        addressSpinnerCity.setAdapter(adapter);
                        break;
                    case 3:
                        addressSpinnerPlace.setAdapter(adapter);
                        break;
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
                switch (spinnerNumber){
                    case 2:
                        addressSpinnerCity.setVisibility(View.GONE);
                        addressSpinnerPlace.setVisibility(View.GONE);
                        break;
                    case 3:
                        addressSpinnerPlace.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

}
