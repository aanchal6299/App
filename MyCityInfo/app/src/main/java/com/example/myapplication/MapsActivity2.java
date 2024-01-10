package com.example.myapplication;
// MapsActivity1.java

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize Places API
        Places.initialize(getApplicationContext(), "AIzaSyCJLAzFVkNk-9Z1kzjsbWoOtpWyByun0vA");

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize location callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        updateLocation(location);
                    }
                }
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        // Enable My Location button and location layer
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Start location updates
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000);

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void updateLocation(Location location) {
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Move camera to the current location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));

        // Add a marker at the current location
        googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("You are here"));

        // Fetch and show nearby restaurants
        fetchNearbyRestaurants(currentLatLng);
    }

    private void fetchNearbyRestaurants(LatLng latLng) {
        PlacesClient placesClient = Places.createClient(this);

        // Define the bounds for the request (e.g., within 5000 meters of the current location)
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(latLng.latitude - 0.05, latLng.longitude - 0.05),
                new LatLng(latLng.latitude + 0.05, latLng.longitude + 0.05)
        );


        // Define the request to find nearby places (restaurants in this case)
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(
                Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG)
        );

        // Execute the request
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        placesClient.findCurrentPlace(request).addOnSuccessListener((response) -> {
            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                Place place = placeLikelihood.getPlace();
                LatLng placeLatLng = place.getLatLng();
                String placeName = place.getName();

                // Add a marker for each nearby restaurant
                googleMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeName));
            }
        }).addOnFailureListener((exception) -> {
            // Handle errors
            exception.printStackTrace();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates when the activity is paused
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume location updates when the activity is resumed
        startLocationUpdates();
    }
}
