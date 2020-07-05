package com.example.ipingpong.views.CoachProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.Notification.SendNotification;
import com.example.ipingpong.controllers.NotificationController;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.LoginActivity;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    EditText messageSubjectEditText, messageBodyEditText;
    CheckBox applicationCheckBox, emailandSMSCheckBox; // smsCheckBox;
    Button sendMessageBtn;

    ArrayList<Integer> playerIDs = new ArrayList<>();
    ArrayList<String> playerEmails = new ArrayList<>();
    ArrayList<String> playerNumbers = new ArrayList<>();

    // need to add proccess dialog

    NotificationController notificationController;

    public MessagesFragment() {
        int i = 0;
        while (i < Constants.playerArrayList.size()){
            playerIDs.add(Constants.playerArrayList.get(i).getUser().getId());
            playerEmails.add(Constants.playerArrayList.get(i).getUser().getEmail());
            playerNumbers.add(Constants.playerArrayList.get(i).getUser().getPhoneNumber());
            i++;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        notificationController = new NotificationController(getContext());

        setInitialization(view);

        return view;
    }

    private void setInitialization(View view){

        messageSubjectEditText = view.findViewById(R.id.messageSubjectEditText);
        messageBodyEditText = view.findViewById(R.id.messageBodyEditText);

        applicationCheckBox = view.findViewById(R.id.applicationCheckBox);
        emailandSMSCheckBox = view.findViewById(R.id.emailandSMSCheckBox);
        //smsCheckBox = view.findViewById(R.id.smsCheckBox);

        sendMessageBtn = view.findViewById(R.id.sendMessageBtn);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotifications();
            }
        });
    }

    private void sendNotifications(){

        if(applicationCheckBox.isChecked()){
            sendApplication();
        }

        if(emailandSMSCheckBox.isChecked()){
            sendEmailandSMS();
        }

    }

    private void sendApplication(){ // uses user IDs

        notificationController.sendMessage(playerIDs, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void sendEmailandSMS(){ // uses email addresses and numbers

        SendNotification sendNotification = new SendNotification();

        sendNotification.massageSubject = messageSubjectEditText.getText().toString();
        sendNotification.massageContent = messageBodyEditText.getText().toString();

        sendNotification.emails = playerEmails;
        sendNotification.numbers = playerNumbers;

        sendNotification.notify();
    }

}
