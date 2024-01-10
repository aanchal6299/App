package com.example.myapplication;

// TransportService.java

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransportService {
    // Define your API endpoints here
    @GET("getTransportInfo")
    Call<TransportResponse> getTransportInfo(@Query("city") String cityName);
}

