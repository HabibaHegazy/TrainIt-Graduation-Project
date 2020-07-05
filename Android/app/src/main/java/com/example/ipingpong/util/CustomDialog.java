package com.example.ipingpong.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.DialogEntities;

import java.util.Objects;

public class CustomDialog extends AppCompatDialogFragment {
    private View view;
    private DialogEntities dialogEntities;
    public TextView counterTextView;
    private  TextView messageSubjectTV, messageBodyTV;

    RadioButton correctWrongRB;
    EditText labelMistakeEditText;

    private String body;
    private String subject;

    public void setBody(String body) {
        this.body = body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CustomDialog(DialogEntities dialogEntities) {
        this.dialogEntities = dialogEntities;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        switch (dialogEntities) {
            case ChangePassword:

                view = inflater.inflate(R.layout.layout_password_dialog, null);

                EditText existPasswordEditText = view.findViewById(R.id.existPasswordEditText);
                EditText newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
                EditText newPasswordConfirmEditText = view.findViewById(R.id.newPasswordConfirmEditText);

                builder.setView(view)
                        .setTitle("Change Password")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { /* nth */}
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // send a temp password to database and send it to email of the user

                            }
                        });
                break;
            case CountDown:

                view = inflater.inflate(R.layout.layout_count_down_dialog, null);
                counterTextView = view.findViewById(R.id.counterTextView);

                builder.setView(view)
                        .setTitle("Training Start In: ")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dont send api to start classification
                            }
                        });
                break;

            case Notification:

                view = inflater.inflate(R.layout.layout_notification_dialog, null);

                System.out.println("came here hello--------");

                messageSubjectTV = view.findViewById(R.id.messageSubjectTextView);
                messageSubjectTV.setText(subject);
                messageBodyTV = view.findViewById(R.id.messageBodyTextView);
                messageBodyTV.setText(body);


                builder.setView(view).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { /* nth */ }
                        });

                break;

            case DatasetData:

                view = inflater.inflate(R.layout.layout_dataset_entry_dialog, null);

                RadioGroup correctWorngRG = view.findViewById(R.id.correctWorngRG);
                final RadioGroup forehandBackhandRG = view.findViewById(R.id.forehandBackhandRG);
                labelMistakeEditText = view.findViewById(R.id.labelMistakeEditText);

                if (correctWorngRG.getCheckedRadioButtonId() != -1)
                {
                    TextView labelMistakeTextView = view.findViewById(R.id.labelMistakeTextView) ;
                    correctWrongRB = view.findViewById(correctWorngRG.getCheckedRadioButtonId());

                    if(correctWrongRB.getText().equals("Wrong Stroke")){
                        labelMistakeTextView.setVisibility(View.VISIBLE);
                        labelMistakeEditText.setVisibility(View.VISIBLE);
                    }else{
                        labelMistakeTextView.setVisibility(View.GONE);
                        labelMistakeEditText.setVisibility(View.GONE);
                    }
                }

                builder.setView(view)
                        .setTitle("Training Start In: ")
                        .setPositiveButton("Enter Information", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // make sure that fields not empty

                                int selectedIdFB = forehandBackhandRG.getCheckedRadioButtonId();
                                RadioButton forehandBackhandRB = view.findViewById(selectedIdFB);

                                // send data info ... etc

                            }
                        })
                        .setNegativeButton("Cancel Dataset Entry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dont enter anything and delete intake dataset
                            }
                        });

                break;
        }


        return builder.create();
    }

}
