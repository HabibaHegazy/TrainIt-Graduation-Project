package com.example.ipingpong.views.PlayerProfile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.PlayerController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.views.Base.ProfileBaseFragments;

import java.util.HashMap;

public class PlayerProfileFragment extends ProfileBaseFragments {

    private TextView playerNumberTextView, playerLevelTextView;
    private EditText playerNumberEditText, playerLevelEditText;
    private RadioGroup playedHandRadioGroup;
    private RadioButton leftRB, rightRB;
    private Player player;
    private HashMap<String, String> playerHashmap = new HashMap<>();
    private PlayerController playerController;



    public PlayerProfileFragment(Context context, Action action, Player player) {
        this.action = action;
        this.player = player;
        this.context = context;
        this.playerController = new PlayerController(context);
        this.userTypeID = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_profile, container, false);

        setBasicSettings(view);

        if(action == Action.ViewUpdate || action == action.View ){
            displayProfileData();

            if(action == Action.View){
                profileActionBtn.setVisibility(View.GONE);
                imageEditBtn.setVisibility(View.GONE);
            }

        }else{

            imageEditBtn.setVisibility(View.GONE);
            profileActionBtn.setText("Add Player");
            enableAllViews();
        }


        return view;
    }


    @Override
    protected void initViews(View view) {
        playerNumberEditText = view.findViewById(R.id.playerNumEditText);
        playerLevelEditText = view.findViewById(R.id.playerLevelEditText);
        playerNumberTextView = view.findViewById(R.id.playerNumberTextView);
        playerLevelTextView = view.findViewById(R.id.playerLevelTextView);

        playedHandRadioGroup = view.findViewById(R.id.playedHandRadioGroup);
        leftRB = view.findViewById(R.id.radioLeftHnad);
        rightRB = view.findViewById(R.id.radioRightHand);

    }

    @Override
    protected void setListeners() {}

    @Override
    protected void enableViews() {
        playerLevelEditText.setEnabled(true);
        playerNumberEditText.setEnabled(true);
        leftRB.setEnabled(true);
        rightRB.setEnabled(true);
    }

    @Override
    protected void disableViews() {
        playerLevelEditText.setEnabled(false);
        playerNumberEditText.setEnabled(false);
        leftRB.setEnabled(false);
        rightRB.setEnabled(false);
    }

    @Override
    protected void validateChange() {

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

        playerNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                playerHashmap.put("playerNumber", playerNumberEditText.getText().toString());
            }
        });

        playerLevelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                playerHashmap.put("level", playerLevelEditText.getText().toString());
            }
        });
    }

    @Override
    public void displayProfileData() {
        this.user = player.getUser();
        displayUserProfileData();

        if (player.getPlayerNumber() == 0){
            playerLevelEditText.setVisibility(View.GONE);
            playerNumberEditText.setVisibility(View.GONE);
            playerLevelTextView.setVisibility(View.GONE);
            playerNumberTextView.setVisibility(View.GONE);
        }
        else {
            // player fields
            playerNumberEditText.setText(Integer.toString(player.getPlayerNumber()));
            playerLevelEditText.setText(Integer.toString(player.getLevel()));
        }

        if(leftRB.getText().toString().toLowerCase().equals(player.getHandUsed())){
            leftRB.setChecked(true);
        }else{
            rightRB.setChecked(true);
        }
    }

    @Override
    public void updateProfileData() {

        if(!playerHashmap.isEmpty()){

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
    }

    @Override
    public void addProfileData(int userID) {

        playerController.addPlayer(playerHashmap, userID, new ControlCallBacks() {
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
        return playerHashmap.size() == 3;
    }
}
