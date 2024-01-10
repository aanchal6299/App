package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

    // EmergencyContacts.java

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

    public class Emergency extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_emergency);

            // Set up Call Now buttons
            Button policeCallButton = findViewById(R.id.policeCallButton);
            policeCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPolice();
                }
            });

            Button ambulanceCallButton = findViewById(R.id.ambulanceCallButton);
            ambulanceCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callAmbulance();
                }
            });

            Button fireCallButton = findViewById(R.id.fireCallButton);
            fireCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callFire();
                }
            });
        }

        public void callPolice() {
            makePhoneCall("100");
        }

        public void callAmbulance() {
            makePhoneCall("102");
        }

        public void callFire() {
            makePhoneCall("101");
        }

        private void makePhoneCall(String phoneNumber) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        }
    }

