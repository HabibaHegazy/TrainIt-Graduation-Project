package com.example.ipingpong.views.Base;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.datasource.local.SendMessage;
import com.example.ipingpong.shared.datasource.local.SharedPrefManager;
import com.example.ipingpong.shared.entities.DialogEntities;
import com.example.ipingpong.util.CustomDialog;
import com.example.ipingpong.views.PlayerProfile.PlayerActivity;

public abstract class BaseSignalIntake extends Fragment implements SensorEventListener {

    protected Button startBtn, endBtn;
    protected ProgressBar progressBar;

    private static final String TAG ="BaseSignalIntake";

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Boolean start = false;
    private String messageString;

    protected void intialization(View view){

        startBtn = view.findViewById(R.id.startBtn);
        endBtn = view.findViewById(R.id.endBtn);
        progressBar = view.findViewById(R.id.progress_bar);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStart();
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEnd();
            }
        });
    }

    public abstract void clickEnd();
    private void clickStart(){
        PlayerActivity.bottomNavigationView.setVisibility(View.GONE);

        final CustomDialog customDialog = new CustomDialog(DialogEntities.CountDown);
        customDialog.show(getActivity().getSupportFragmentManager(), "Count Down");

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                customDialog.counterTextView.setText("" + millisUntilFinished / 1000);

                MediaPlayer AlarmMusic;
                AlarmMusic = MediaPlayer.create(getContext(), R.raw.music1);
                AlarmMusic.setLooping(false);
                AlarmMusic.start();
            }

            public void onFinish() {
                customDialog.dismiss();
                startBtn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);

                MediaPlayer AlarmMusic;
                AlarmMusic = MediaPlayer.create(getContext(), R.raw.music2);
                AlarmMusic.setLooping(false);
                AlarmMusic.start();

                baseSetUp();
                Start();

                //Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                //startActivity(intent);
            }

        }.start();
    }

    private void baseSetUp(){
        Log.d(TAG,"onCreate: Initializing Sensor Services ");
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        if(accelerometer != null && gyroscope != null){
            Log.d(TAG,"onCreate: Registered accelerometer and gyroscope listeners  ");
        }else if(accelerometer == null ){
            Log.d(TAG,"either AccX, AccY or AccZ is Not supported  ");
        }else if(gyroscope == null){
            Log.d(TAG,"either GyroX, GyroY or GyroZ is Not supported   ");
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor sensor = sensorEvent.sensor;
        if((sensor.getType() == Sensor.TYPE_ACCELEROMETER) && start){

            Log.d(TAG,"onSensorChanged_ACC: X: "+ sensorEvent.values[0] +" Y: " + sensorEvent.values[1]+" Z: " + sensorEvent.values[2]);
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            messageString = "acc"+"@"+sensorEvent.values[0]+"@"+sensorEvent.values[1]+"@"+sensorEvent.values[2]+"@"+ts;
            new SendMessage().execute(messageString);
        }

        else if((sensor.getType() == Sensor.TYPE_GYROSCOPE) && start){

            Log.d(TAG,"onSensorChanged_Gyro: X: "+ sensorEvent.values[0] +" Y: " + sensorEvent.values[1]+" Z: " + + sensorEvent.values[2]);
            Long tsLong2 = System.currentTimeMillis()/1000;
            String ts2 = tsLong2.toString();

            messageString = "gyro"+"@"+sensorEvent.values[0]+"@"+sensorEvent.values[1]+"@"+sensorEvent.values[2]+"@"+ts2;
            new SendMessage().execute(messageString);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {}

    private void Start() {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        String currentPlayerId = String.valueOf(sharedPrefManager.getUser().getId());
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        start = true;
        messageString = "true"+"@"+currentPlayerId+"@"+ts;
        new SendMessage().execute(messageString);
    }

    protected void Stop() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        start = false;
        messageString = "false"+"@"+ts;
        new SendMessage().execute(messageString);
    }

}
