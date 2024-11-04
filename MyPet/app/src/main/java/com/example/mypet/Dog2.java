package com.example.mypet; //CD21068

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dog2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog2);

        Button nextButton = findViewById(R.id.button12); // Assuming button6 is your "Next" button

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the Contact Us page or any other desired activity
                Intent selectionIntent = new Intent(Dog2.this, Diary.class);
                startActivity(selectionIntent);
            }
        });
    }
}
