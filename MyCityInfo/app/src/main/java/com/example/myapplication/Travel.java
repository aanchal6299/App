package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Travel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Button flight = findViewById(R.id.flight);
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Travel.this, Flight.class));
            }
        });
        // Find Routes Button
        Button findRoutesButton = findViewById(R.id.findRoutesButton);

        // Set OnClickListener for Find Routes Button
        findRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from EditText fields
                EditText sourceEditText = findViewById(R.id.sourceEditText);
                EditText destinationEditText = findViewById(R.id.destinationEditText);

                String source = sourceEditText.getText().toString();
                String destination = destinationEditText.getText().toString();

                // Redirect to TransportActivity with source and destination values
                redirectToTransportActivity(source, destination);
            }
        });
    }

    // Function to redirect to TransportActivity with source and destination values
    private void redirectToTransportActivity(String source, String destination) {
        // In your previous activity
        Intent intent = new Intent(this, TransportActivity.class);
        intent.putExtra("sourceEditText", source);
        intent.putExtra("destinationEditText", destination);
        startActivity(intent);
    }
}
