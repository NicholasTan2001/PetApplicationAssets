package com.example.mypet; //CD21068

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Booking extends AppCompatActivity {
    private EditText eTo, eSubject, eMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        eTo = findViewById(R.id.Email);
        eSubject = findViewById(R.id.Appointment);
        eMessage = findViewById(R.id.Description);

        Button send = findViewById(R.id.button3);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{eTo.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, eSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, eMessage.getText().toString());
        startActivity(Intent.createChooser(emailIntent, "Choose an email"));
    }
}

