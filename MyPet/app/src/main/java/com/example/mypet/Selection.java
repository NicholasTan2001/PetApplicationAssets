package com.example.mypet; //CD21068

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Intent intent = getIntent();
    }

    public void goToNextPage(View view) {


        if (view.getId() == R.id.button4) {
            // Go to Lower-Level Gallery
            Intent lowerLevelIntent = new Intent(this,Dog.class);
            startActivity(lowerLevelIntent);
        } else if (view.getId() == R.id.button5) {
            // Go to Upper-Level Gallery
            Intent upperLevelIntent = new Intent(this,Cat.class);
            startActivity(upperLevelIntent);
        } else {
            // Default behavior, go to the NextActivity
            Intent intent = new Intent(this,Selection.class);
            startActivity(intent);
        }

    }
}