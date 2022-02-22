package com.example.launcher;

public class RecyclerViewData {

    String city_name;
    String country_name;
    String temperature_celsius;
    String description_detail;
    public RecyclerViewData(String city_name, String country_name,
                            String temperature_celsius, String description_detail) {
        this.city_name = city_name;
        this.country_name = country_name;
        this.temperature_celsius = temperature_celsius;
        this.description_detail = description_detail;
    }
}

