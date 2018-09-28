package com.example.jasminrose.remotewebcam;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor proximitySensor;
    Button btnStart, btnStop;
    boolean listening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_PROXIMITY);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
    }

    public void startOnClick(View view) {
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        listening = true;
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        Toast.makeText(this, "Listening to proximity sensor...", Toast.LENGTH_SHORT).show();
    }

    public void stopOnClick(View view) {
        sensorManager.unregisterListener(this);
        listening = false;
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        Toast.makeText(this, "Listening stopped", Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
        if(listening)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        if(!listening)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0.0) {
            MessageSender messageSender = new MessageSender();
            messageSender.execute(String.valueOf(event.values[0]));
            Toast.makeText(this, "Picture taken!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
