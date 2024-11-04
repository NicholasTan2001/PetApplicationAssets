package com.example.mypet; //CD21068


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Shake extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isAccelSensorAvailable;
    private Vibrator vibrator;
    private static final float ACCELERATION_THRESHOLD = 12.0f; // Adjust as needed
    private static final long[] VIBRATION_PATTERN = {0, 100, 200, 300}; // Adjust pattern as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        // Initialize sensor manager and accelerometer sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                isAccelSensorAvailable = true;
            }
        }

        // Initialize vibrator service
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Find the booking button by its id
        Button bookingButton = findViewById(R.id.booking);

        // Set click listener for the booking button
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Booking Activity
                openBookingActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccelSensorAvailable) {
            // Register the accelerometer sensor listener
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelSensorAvailable) {
            // Unregister the accelerometer sensor listener to save battery
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing for now
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate total acceleration
            double acceleration = Math.sqrt(x * x + y * y + z * z);

            // Check if acceleration exceeds threshold
            if (acceleration > ACCELERATION_THRESHOLD) {
                // Vibrate with custom pattern
                vibrateWithPattern();
                // Open Booking Activity
                openAppointmentActivity();
            }
        }
    }

    private void vibrateWithPattern() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VIBRATION_PATTERN, -1); // Vibrate once with custom pattern
        }
    }

    private void openAppointmentActivity() {
        Intent appointmentIntent = new Intent(this, Appointment.class);
        startActivity(appointmentIntent);
        // Ensure the activity doesn't stack up in the back stack
        finish();
    }

    private void openBookingActivity() {
        Intent bookingIntent = new Intent(this, Booking.class);
        startActivity(bookingIntent);
    }
}
