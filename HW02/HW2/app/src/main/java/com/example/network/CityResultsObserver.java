package com.example.network;

import com.example.datamodel.City;

import java.util.HashMap;



public interface  CityResultsObserver {
    public void receiveCitiesList(HashMap<String, City> citiesCollection);
    public void onFailure(Throwable cause);
}
