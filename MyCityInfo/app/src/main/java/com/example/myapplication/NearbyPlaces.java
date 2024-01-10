package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NearbyPlaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        Button restaurantsButton = findViewById(R.id.restaurantsButton);
        restaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity when "Nearby Restaurants" button is clicked
                startActivity(new Intent(NearbyPlaces.this, MapsActivity.class));
            }
        });

        Button hospitalButton = findViewById(R.id.hospitalsButton);
        hospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Hospitals activity when "Nearby Hospitals" button is clicked
                startActivity(new Intent(NearbyPlaces.this, Hospitals.class));
            }
        });

        Button banksButton = findViewById(R.id.banksButton);
        banksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Banks activity when "Nearby Banks" button is clicked
                startActivity(new Intent(NearbyPlaces.this, Banks.class));
            }
        });

        Button collegesButton = findViewById(R.id.collegesButton);
        collegesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Colleges activity when "Nearby Colleges" button is clicked
                startActivity(new Intent(NearbyPlaces.this, Colleges.class));
            }
        });

        Button more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Colleges activity when "Nearby Colleges" button is clicked
                startActivity(new Intent(NearbyPlaces.this, More.class));
            }
        });
    }
}
