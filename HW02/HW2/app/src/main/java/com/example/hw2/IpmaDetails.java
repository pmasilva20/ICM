package com.example.hw2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.datamodel.City;
import com.example.datamodel.Weather;
import com.example.datamodel.WeatherType;
import com.example.network.CityResultsObserver;
import com.example.network.ForecastForACityResultsObserver;
import com.example.network.IpmaWeatherClient;
import com.example.network.WeatherTypesResultsObserver;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IpmaDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IpmaDetails extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "com.example.IpmaDetails";

    private String mParam1;
    private String mParam2;

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<String,Weather > weatherForecast = new HashMap<>();

    public IpmaDetails() {
        // Required empty public constructor
    }


    public static IpmaDetails newInstance(String param1, String param2) {
        IpmaDetails fragment = new IpmaDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ipma_details, container, false);

    }

    public void getForecast(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                IpmaDetails.this.cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    getForecast2(locationId);
                } else {
                    Log.e(TAG,"unknown city: " + city);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.e(TAG,"Failed to get cities list!");
            }
        });
    }
    public void getForecast2(int localId){
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    weatherForecast.put(day.getForecastDate(),day);
                }
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewDay);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);

                RecyclerViewDayListAdapter adapter = new RecyclerViewDayListAdapter(weatherForecast.keySet().toArray(new String[0]),IpmaDetails.this);
                recyclerView.setAdapter(adapter);
                updateIpmaDetails(weatherForecast.keySet().iterator().next());
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.e(TAG,"Couldnt get weather forecast");
            }
        });
    }

    public void clickedDate(String date) {
        updateIpmaDetails(date);

    }
    private void updateIpmaDetails(String date){
        Weather forecast = weatherForecast.get(date);
        ((TextView)(getView().findViewById(R.id.textViewDate))).setText(date);
        ((TextView)(getView().findViewById(R.id.textViewPrecipation))).setText(String.valueOf(forecast.getPrecipitaProb()));
        ((TextView)(getView().findViewById(R.id.textViewWindDirection))).setText(forecast.getPredWindDir());
        ((TextView)(getView().findViewById(R.id.textViewWindSpeed))).setText(String.valueOf(forecast.getClassWindSpeed()));
        String temp = String.valueOf(forecast.getTMin())+"ºC - " + String.valueOf(forecast.getTMax())+"ºC";
        ((TextView)(getView().findViewById(R.id.textViewTemperatureInterval))).setText(temp);
    }
}