package com.example.myapplication;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private Weather[] weather;

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public static class Main {
        @SerializedName("temp")
        private double temperature;

        public double getTemperature() {
            return temperature;
        }
    }

    public static class Weather {
        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }
    }
}
