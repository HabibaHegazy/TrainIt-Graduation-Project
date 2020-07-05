package com.example.ipingpong.views.Base;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.AddressController;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectAllAddresses;
import com.example.ipingpong.controllers.UserController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Address;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public abstract class ProfileBaseFragments extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private EditText firstNameET, lastNameET, phoneNumberET, emailET;
    private Spinner addressSpinnerCountry, addressSpinnerCity, addressSpinnerPlace;
    private RadioGroup genderRadioGroup;
    private RadioButton femaleRB, maleRB;
    private ImageView profileImg;
    protected Button profileActionBtn, imageEditBtn, birthdateProfileBtn;
    protected ProgressDialog dialog;
    protected Action action;
    public HashMap<String, String> userHashmap = new HashMap<>();
    protected User user;
    public ArrayAdapter<Address> adapter;
    protected int userTypeID = -1;

    protected Context context;
    public AddressController addressController;
    private UserController userController;

    protected void setBasicSettings(View view){

        addressController = new AddressController(context);
        userController = new UserController(context);
        initAllViews(view);
        setAllListeners();
        validateMainChange();
        dialog.dismiss();
    }

    protected abstract void initViews(View view);
    private void initAllViews(View view) {

        dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);

        firstNameET = view.findViewById(R.id.firstNameEditText);
        lastNameET = view.findViewById(R.id.lastNameEditText);
        phoneNumberET = view.findViewById(R.id.phoneProfileEditText);
        emailET = view.findViewById(R.id.emailProfileEditText);

        addressSpinnerCountry = view.findViewById(R.id.addressSpinnerCountry);
        addressSpinnerCity = view.findViewById(R.id.addressSpinnerCity);
        addressSpinnerPlace = view.findViewById(R.id.addressSpinnerPlace);

        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        femaleRB = view.findViewById(R.id.radioFemaleProfile);
        maleRB = view.findViewById(R.id.radioMaleProfile);

        profileImg = view.findViewById(R.id.profile_img);

        profileActionBtn = view.findViewById(R.id.profileActionBtn);
        imageEditBtn = view.findViewById(R.id.imageEditBtn);
        birthdateProfileBtn = view.findViewById(R.id.birthdateProfileBtn);

        initViews(view);
    }

    protected abstract void setListeners();
    private void setAllListeners() {
        profileImg.setOnClickListener(this);
        imageEditBtn.setOnClickListener(this);
        profileActionBtn.setOnClickListener(this);
        birthdateProfileBtn.setOnClickListener(this);

        getAdderesses(0, 1);

        addressSpinnerCountry.setOnItemSelectedListener(this);
        addressSpinnerCity.setOnItemSelectedListener(this);
        addressSpinnerPlace.setOnItemSelectedListener(this);

        setListeners();
    }

    protected abstract void enableViews();
    protected void enableAllViews(){
        firstNameET.setEnabled(true);
        lastNameET.setEnabled(true);
        phoneNumberET.setEnabled(true);
        emailET.setEnabled(true);
        addressSpinnerCountry.setEnabled(true);
        addressSpinnerCity.setEnabled(true);
        addressSpinnerPlace.setEnabled(true);
        femaleRB.setEnabled(true);
        maleRB.setEnabled(true);
        birthdateProfileBtn.setEnabled(true);
        profileActionBtn.setEnabled(true);
        imageEditBtn.setEnabled(true);
        profileImg.setEnabled(true);

        enableViews();
    }

    protected abstract void disableViews();
    private void disableAllViews(){
        firstNameET.setEnabled(false);
        lastNameET.setEnabled(false);
        phoneNumberET.setEnabled(false);
        emailET.setEnabled(false);
        addressSpinnerCountry.setEnabled(false);
        addressSpinnerCity.setEnabled(false);
        addressSpinnerPlace.setEnabled(true);
        femaleRB.setEnabled(false);
        maleRB.setEnabled(false);
        birthdateProfileBtn.setEnabled(false);
        profileActionBtn.setEnabled(false);
        imageEditBtn.setEnabled(false);
        profileImg.setEnabled(false);

        disableViews();
    }

    private void getAdderesses(final int parentID, final int spinnerNumber){

        addressController.getAllAddresses(parentID, new SelectAllAddresses() {
            @Override
            public void onSuccess(ArrayList<Address> addressesList) {

                adapter = new ArrayAdapter<>(context,  android.R.layout.simple_spinner_dropdown_item, addressesList);
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
                Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
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

    protected abstract void validateChange();
    private void validateMainChange(){

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

        validateChange();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageEditBtn:
                enableAllViews();
                break;
            case R.id.profileActionBtn:
                disableAllViews();
                if (action == Action.ViewUpdate) {
                    updateUserProfileData();
                } else {
                    addUserProfileData();
                }
                break;
            case R.id.birthdateProfileBtn:
                showDatePickerDialog(this);
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        birthdateProfileBtn.setText(year + "-" + (month+1) + "-" + day);
    }

    private void showDatePickerDialog (DatePickerDialog.OnDateSetListener listener) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, month, day);
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
                    //Toast.makeText(context, ((Address) parent.getSelectedItem()).getAddressName(), Toast.LENGTH_LONG).show();
                    break;
            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public abstract void displayProfileData();
    protected void displayUserProfileData(){
        firstNameET.setText(user.getFirstName());
        lastNameET.setText(user.getLastName());
        emailET.setText(user.getEmail());
        phoneNumberET.setText(user.getPhoneNumber());
        birthdateProfileBtn.setText(user.getBirthdate());
        if(femaleRB.getText().equals(user.getGender())){
            femaleRB.setChecked(true);
        }else{
            maleRB.setChecked(true);
        }

        // choose from the spinners the right ones
    }

    public abstract void updateProfileData();
    private void updateUserProfileData(){

        if(!birthdateProfileBtn.getText().toString().equals(user.getBirthdate())){
            userHashmap.put("birthdate", birthdateProfileBtn.getText().toString());
        }

        if(!userHashmap.isEmpty()){
            userController.updateUser(userHashmap, user.getId(), new ControlCallBacks() {
                @Override
                public void onSuccess(String success) {
                    Toast.makeText(context, success, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String reason) {
                    Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
                }
            });
        }

        updateProfileData();
    }

    public abstract void addProfileData(int userID);
    private void addUserProfileData(){
        enableAllViews();

        userHashmap.put("birthdate", birthdateProfileBtn.getText().toString());
        userHashmap.put("usertypeID", String.valueOf(userTypeID));

        if(userHashmap.size() == 8 && validateAddFields()){
            userController.addUser(userHashmap, new InsertUserCallBack() {
                @Override
                public void onSuccess(String success, int userID) {
                    Toast.makeText(context, success, Toast.LENGTH_LONG).show();
                    addProfileData(userID);
                }
                @Override
                public void onFailure(String reason) {
                    Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(context, "Please Fill all Fields", Toast.LENGTH_LONG).show();
            enableAllViews();
        }

    }

    public abstract boolean validateAddFields();

}
