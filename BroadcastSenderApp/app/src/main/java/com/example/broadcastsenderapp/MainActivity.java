package com.example.broadcastsenderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;

import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import java.lang.Math;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    TextView value1;
    TextView value2;
    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorMan.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        sensorMan.unregisterListener(this);
    }

    public void sendLowerDownVolumeBroadcast() {
        Intent intent = new Intent("com.example.myapplication.ON_TABLE_TRIGGER");
        sendBroadcast(intent);
    }
    public void sendVolumeUpBroadcast() {
        Intent intent = new Intent("com.example.myapplication.IN_POCKET_TRIGGER");
        sendBroadcast(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean isBright=false;
        boolean movement = false;
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            value1.setText(""+sensorEvent.values[0]);

            if(sensorEvent.values[0] > 50){
                isBright = true;
            }
            else{
                isBright = false;
            }
        }
        if(sensorEvent.sensor.getType() == sensor.TYPE_ACCELEROMETER){
            mGravity = sensorEvent.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            value2.setText("" + mAccel);
            if(mAccel > 7.5){
                movement = true;
            }
            else{
                movement = false;
            }

        }
        if(!isBright && movement){
            sendVolumeUpBroadcast();
        }
        if(isBright && !movement){
            sendLowerDownVolumeBroadcast();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}