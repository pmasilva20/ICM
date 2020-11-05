package com.example.network;

import com.example.datamodel.WeatherType;

import java.util.HashMap;


public interface WeatherTypesResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}
