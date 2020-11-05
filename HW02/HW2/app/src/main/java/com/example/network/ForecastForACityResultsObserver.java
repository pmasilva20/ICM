package com.example.network;

import com.example.datamodel.Weather;

import java.util.HashMap;
import java.util.List;



public interface ForecastForACityResultsObserver {
    public void receiveForecastList(List<Weather> forecast);
    public void onFailure(Throwable cause);
}
