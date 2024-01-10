package com.example.myapplication;

// TransportResponse.java

import com.google.gson.annotations.SerializedName;

public class TransportResponse {
    @SerializedName("transportInfo")
    private String transportInfo;

    // Add other fields as needed

    public String getTransportInfo() {
        return transportInfo;
    }
}
