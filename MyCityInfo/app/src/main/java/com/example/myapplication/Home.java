package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private TextView weatherInfoTextView;
    private TextView cityNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cityNameTextView = findViewById(R.id.cityNameTextView);

        weatherInfoTextView = findViewById(R.id.weatherInfoTextView);

        // Fetch and display weather information
        fetchWeatherInfo();
        displayCityName();
        Button nearby = findViewById(R.id.nearBy);
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity when "Nearby Restaurants" button is clicked
                startActivity(new Intent(Home.this, NearbyPlaces.class));
            }
        });
        Button hotels = findViewById(R.id.hotels);
        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity when "Nearby Restaurants" button is clicked
                startActivity(new Intent(Home.this, Hotels.class));
            }
        });
        Button police = findViewById(R.id.police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity when "Nearby Restaurants" button is clicked
                startActivity(new Intent(Home.this, Police.class));
            }
        });
        Button travel = findViewById(R.id.travel);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity when "Nearby Restaurants" button is clicked
                startActivity(new Intent(Home.this, Travel.class));
            }
        });
        Button emergency = findViewById(R.id.emergency);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Emergency.class));
            }
        });
//
    }

    private void fetchWeatherInfo() {
        // Replace "YOUR_API_KEY" with your OpenWeatherMap API key
        String apiKey = "217d98b53d8fd007a962b2da7db1fc54";
        String cityName = getIntent().getStringExtra("CITY_NAME");  // Replace with your city name

        // Create a Gson instance
        Gson gson = new GsonBuilder().create();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Create WeatherService instance
        WeatherService weatherService = retrofit.create(WeatherService.class);

        // Make API request
        Call<WeatherResponse> call = weatherService.getWeather(cityName, apiKey);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        double temperature = weatherResponse.getMain().getTemperature();
                        String weatherIcon = weatherResponse.getWeather()[0].getIcon(); // Assuming the first element in the array
                        updateWeatherInfo(temperature, weatherIcon);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void displayCityName() {
        // Retrieve the city name from the Intent
        String cityName = getIntent().getStringExtra("CITY_NAME");

        // Display the city name in the TextView
        cityNameTextView.setText(cityName);
    }


    private void updateWeatherInfo(double temperature, String weatherIcon) {
        runOnUiThread(() -> {
            String weatherInfo = String.format("%.1f°C", temperature - 273.15);
            weatherInfoTextView.setText(weatherInfo);

            // Log the weather icon for debugging
            Log.d("Weather", "Temperature: " + temperature);
            Log.d("Weather", "Weather Icon: " + weatherIcon);
            Log.d("Weather", "Weather Icon: " + weatherIcon);

            // Display weather icon based on the icon code
            displayWeatherIcon(weatherIcon);
        });
    }

    private void displayWeatherIcon(String weatherIcon) {
        // Log the weather icon for debugging
        Log.d("Weather", "Displaying Icon: " + weatherIcon);

        // Assume you have image views for different weather conditions
        ImageView sunnyIcon = findViewById(R.id.sunnyIcon);
        ImageView rainyIcon = findViewById(R.id.rainyIcon);
        ImageView cloudyIcon = findViewById(R.id.cloudyIcon);
        ImageView exhaustIcon = findViewById(R.id.exhaust_cloud);

        // Show/hide image views based on the weather condition
        switch (weatherIcon) {
            case "01d": // clear sky (day)
                sunnyIcon.setVisibility(View.VISIBLE);
                rainyIcon.setVisibility(View.GONE);
                cloudyIcon.setVisibility(View.GONE);
                break;
            case "10d": // rain (day)
                sunnyIcon.setVisibility(View.GONE);
                rainyIcon.setVisibility(View.VISIBLE);
                cloudyIcon.setVisibility(View.GONE);
                break;
            case "03d": // cloudy (day)
                sunnyIcon.setVisibility(View.GONE);
                rainyIcon.setVisibility(View.GONE);
                cloudyIcon.setVisibility(View.VISIBLE);
                break;
            case "04d": // cloudy (day)
                sunnyIcon.setVisibility(View.GONE);
                rainyIcon.setVisibility(View.GONE);
                cloudyIcon.setVisibility(View.GONE);
                exhaustIcon.setVisibility(View.VISIBLE);
                break;
            // Add more cases for other weather conditions as needed
            default:
                sunnyIcon.setVisibility(View.GONE);
                rainyIcon.setVisibility(View.GONE);
                cloudyIcon.setVisibility(View.VISIBLE);
                break;
        }
    }
    // Inside HomeActivity.java
    private void showRouteToRestaurant(String restaurantLocation) {
        // Use Google Maps Directions API to get directions
        // Display the route on the map
        // Example: Call the Google Maps Directions API and draw the route
        // ...

        // For simplicity, you can use a Toast to confirm the click
        Toast.makeText(this, "Showing route to Restaurant 1", Toast.LENGTH_SHORT).show();
    }


}
