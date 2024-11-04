package com.example.mypet; //CD21068

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button nextButton = findViewById(R.id.button3); // Assuming button3 is your "Next" button

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the Contact Us page or any other desired activity
                Intent selectionIntent = new Intent(Calculator.this, Selection.class);
                startActivity(selectionIntent);
            }
        });
    }
    public void DOGAGE(View v) {
        EditText et1 = (EditText) findViewById(R.id.editTextNumber);
        EditText et3 = (EditText) findViewById(R.id.editTextResult);

        // Get the pet's age from the EditText
        int petAge = Integer.parseInt(et1.getText().toString());

        // Calculate the human age for a dog
        int humanAge;
        if (petAge <= 2) {
            humanAge = petAge * 10;
        } else {
            humanAge = 20 + ((petAge - 2) * 4);
        }

        // Display the result in the EditText
        et3.setText("Dog age in human years: " + humanAge);

        // Display a Toast message depending on the result
        String ageCategory;
        if (humanAge <= 15) {
            ageCategory = "Your dog is a adorable Puppy.";
        } else if (humanAge <= 25) {
            ageCategory = "Your dog is a  sprightly Junior.";
        } else if (humanAge <= 50) {
            ageCategory = "Your dog is an mature Adult.";
        } else if (humanAge <= 75) {
            ageCategory = "Your dog is a  gentle Senior.";
        } else {
            ageCategory = "Your dog is venerable Geriatric.";
        }

        Toast.makeText(this, ageCategory, Toast.LENGTH_LONG).show();
    }

    public void CATAGE(View v) {
        EditText et1 = (EditText) findViewById(R.id.editTextNumber);
        EditText et3 = (EditText) findViewById(R.id.editTextResult);

        // Get the cat's age from the EditText
        int catAge = Integer.parseInt(et1.getText().toString());

        // Calculate the human age for a cat
        int humanAge;
        if (catAge == 1) {
            humanAge = 15;
        } else if (catAge == 2) {
            humanAge = 25;
        } else {
            humanAge = 25 + ((catAge - 2) * 4);
        }

        // Display the result in the EditText
        et3.setText("Cat age in human years: " + humanAge);

        // Display a Toast message depending on the result
        String ageCategory;
        if (humanAge <= 15) {
            ageCategory = "Your cat is a cute Kitten.";
        } else if (humanAge <= 25) {
            ageCategory = "Your cat is a lively Junior.";
        } else if (humanAge <= 50) {
            ageCategory = "Your cat is an mature Adult.";
        } else if (humanAge <= 75) {
            ageCategory = "Your cat is a graceful Senior.";
        } else {
            ageCategory = "Your cat is Geriatric.";
        }

        Toast.makeText(this, ageCategory, Toast.LENGTH_LONG).show();
    }
}
