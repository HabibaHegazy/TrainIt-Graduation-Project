package com.example.ipingpong.views.ClubManagerProfile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.SelectAllAddresses;
import com.example.ipingpong.controllers.ClubController;
import com.example.ipingpong.controllers.PlayerController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Address;
import com.example.ipingpong.shared.entities.Club;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Base.ProfileBaseFragments;

import java.util.ArrayList;
import java.util.HashMap;

public class ClubManagerProfileFragment extends ProfileBaseFragments {

    private EditText clubNameEditText, clubRateEditText;
    private Spinner clubAddressSpinnerCountry, clubAddressSpinnerCity, clubAddressSpinnerPlace;
    private HashMap<String, String> clubHashmap = new HashMap<>();
    private ClubController clubController;

    public ClubManagerProfileFragment(Context context,Action action, User user) {
        this.action = action;
        this.context = context;
        this.clubController = new ClubController(context);
        this.userTypeID = 3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_manager_profile, container, false);

         // its always view Update

        if(action == Action.Add){
            imageEditBtn.setVisibility(View.GONE);
            profileActionBtn.setText("Add Club Manager");
            enableAllViews();
        }else{
            setBasicSettings(view);
            displayProfileData();
        }



        return view;
    }


    @Override
    protected void initViews(View view) {
        clubNameEditText = view.findViewById(R.id.clubNameEditText);
        clubRateEditText = view.findViewById(R.id.clubRateEditText);

        clubAddressSpinnerCountry = view.findViewById(R.id.clubAddressSpinnerCountry);
        clubAddressSpinnerCity = view.findViewById(R.id.clubAddressSpinnerCity);
        clubAddressSpinnerPlace = view.findViewById(R.id.clubAddressSpinnerPlace);
    }

    private void getAdderesses(final int parentID, final int spinnerNumber){

        addressController.getAllAddresses(parentID, new SelectAllAddresses() {
            @Override
            public void onSuccess(ArrayList<Address> addressesList) {

                adapter = new ArrayAdapter<>(context,  android.R.layout.simple_spinner_dropdown_item, addressesList);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                switch (spinnerNumber){
                    case 1:
                        clubAddressSpinnerCountry.setAdapter(adapter);
                        break;
                    case 2:
                        clubAddressSpinnerCity.setAdapter(adapter);
                        break;
                    case 3:
                        clubAddressSpinnerPlace.setAdapter(adapter);
                        break;
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
                switch (spinnerNumber){
                    case 2:
                        clubAddressSpinnerCity.setVisibility(View.GONE);
                        clubAddressSpinnerPlace.setVisibility(View.GONE);
                        break;
                    case 3:
                        clubAddressSpinnerPlace.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

    @Override
    protected void setListeners() {
        clubAddressSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAdderesses(((Address) parent.getSelectedItem()).getId(), 2);
                clubAddressSpinnerCity.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        clubAddressSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAdderesses(((Address) parent.getSelectedItem()).getId(), 3);
                clubAddressSpinnerPlace.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        clubAddressSpinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userHashmap.put("addressID", String.valueOf(((Address) parent.getSelectedItem()).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void enableViews() {
        clubNameEditText.setEnabled(true);
        clubRateEditText.setEnabled(true);

        clubAddressSpinnerCountry.setEnabled(true);
        clubAddressSpinnerCity.setEnabled(true);
        clubAddressSpinnerPlace.setEnabled(true);
    }

    @Override
    protected void disableViews() {
        clubNameEditText.setEnabled(false);
        clubRateEditText.setEnabled(false);

        clubAddressSpinnerCountry.setEnabled(false);
        clubAddressSpinnerCity.setEnabled(false);
        clubAddressSpinnerPlace.setEnabled(false);
    }

    @Override
    protected void validateChange() {

        // spinners

        clubNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                clubHashmap.put("playerNumber", clubNameEditText.getText().toString());
            }
        });

        clubRateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                clubHashmap.put("level", clubRateEditText.getText().toString());
            }
        });
    }

    @Override
    public void displayProfileData() {
        displayUserProfileData();

        // player fields
        clubNameEditText.setText(user.getClub().getName());
        clubRateEditText.setText(Double.toString(user.getClub().getRate()));

        // spinners show

    }

    @Override
    public void updateProfileData() {

        /*if(!clubHashmap.isEmpty()){


            playerController.updatePlayer(playerHashmap, player.getId(), new ControlCallBacks() {
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
         */
    }

    @Override
    public void addProfileData(int userID) {

        clubController.addClub(clubHashmap, new ControlCallBacks() {
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

    @Override
    public boolean validateAddFields() {
        return clubHashmap.size() == 3;
    }
}
