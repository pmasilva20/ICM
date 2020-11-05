package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent it = getIntent();
        String city_choosen = it.getStringExtra(MainActivity.CITY_CHOOSEN);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment current = fragmentManager.findFragmentById(R.id.fragment_ipma_details);
        ((IpmaDetails)current).getForecast(city_choosen);
    }
}