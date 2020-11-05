package com.example.hw2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datamodel.City;
import com.example.datamodel.WeatherType;
import com.example.network.CityResultsObserver;
import com.example.network.IpmaWeatherClient;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IpmaList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IpmaList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "com.example.IpmaList";

    private String mParam1;
    private String mParam2;

    interface OnFragmentIPMAListListener{
        void onChoosingCity(String choice);
    }
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    OnFragmentIPMAListListener listener;

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;

    public IpmaList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IpmaList.
     */
    public static IpmaList newInstance(String param1, String param2) {
        IpmaList fragment = new IpmaList();
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
        View inflatedView = inflater.inflate(R.layout.fragment_ipma_list, container, false);

        recyclerView = (RecyclerView) inflatedView.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                IpmaList.this.cities = citiesCollection;
                String[] myDataset = IpmaList.this.cities.keySet().toArray(new String[0]);
                mAdapter = new RecyclerViewAdapter(myDataset,IpmaList.this);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.e(TAG,"Couldnt get city list");
            }
        });

        // Inflate the layout for this fragment
        return inflatedView;
    }

    public void clickedCity(String choice){
        listener.onChoosingCity(choice);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentIPMAListListener) {
            listener = (OnFragmentIPMAListListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }
}