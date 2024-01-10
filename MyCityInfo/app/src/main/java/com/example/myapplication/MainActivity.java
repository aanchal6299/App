package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button redirectButton = findViewById(R.id.redirectButton);
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToHomePage();
            }
        });
    }

    // Function to redirect to the HomeActivity
    private void redirectToHomePage() {
        // Retrieve city name from EditText
        EditText cityNameEditText = findViewById(R.id.cityName);
        String cityName = cityNameEditText.getText().toString().trim();

        // Check if the city name is empty
        if (cityName.isEmpty()) {
            // Show a message asking the user to fill in the city name
            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
        } else {
            // Capitalize the first letter of the city name
            cityName = capitalizeFirstLetter(cityName);

            // Create an intent to start the Home activity
            Intent intent = new Intent(this, Home.class);

            // Pass city name to Home activity
            intent.putExtra("CITY_NAME", cityName);

            // Start the Home activity
            startActivity(intent);
        }
    }
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}