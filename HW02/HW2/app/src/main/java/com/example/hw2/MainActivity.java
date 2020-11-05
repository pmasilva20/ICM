package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IpmaList.OnFragmentIPMAListListener{


    public static final String CITY_CHOOSEN ="com.example.hw2.mainactivity";
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_ipma_details) != null) {
            mTwoPane = true;
        }

    }

    @Override
    public void onChoosingCity(String choice) {
        if(mTwoPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment current = fragmentManager.findFragmentById(R.id.fragment_ipma_details);
            ((IpmaDetails)current).getForecast(choice);
        }
        else{
            Intent intent = new Intent(this,DetailsActivity.class);
            intent.putExtra(CITY_CHOOSEN, choice);
            startActivity(intent);
        }
    }
}