package com.example.mypet; //CD21068

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Appointment extends AppCompatActivity {
    String item2[], price2[];
    RecyclerView recyclerView;
    int images[] = {R.drawable.routine, R.drawable.vaccine, R.drawable.dental, R.drawable.trim, R.drawable.bathing};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Fetching the string arrays and recycler view
        item2 = getResources().getStringArray(R.array.items);
        price2 = getResources().getStringArray(R.array.price);
        recyclerView = findViewById(R.id.recycleView);

        // Setting up the adapter and recycler view
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(this, item2, price2, images);
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
