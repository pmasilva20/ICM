package com.example.ca1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class speedDial extends AppCompatActivity {
    public static final String CONTACT_NUMBER = "icm.CA1.extra.contact_number";
    public static final String CONTACT_NAME = "icm.CA1.extra.contact_name";
    public static final String CONTACT_ID = "icm.CA1.extra.contact_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial);
    }

    public void contactSaved(View view){
        String contact_name = ((EditText)findViewById(R.id.contactedit)).getText().toString();
        String contact_phone = ((EditText)findViewById(R.id.numberedit)).getText().toString();
        Intent main_activity_it = getIntent();
        main_activity_it.putExtra(CONTACT_NAME,contact_name);
        main_activity_it.putExtra(CONTACT_NUMBER,contact_phone);
        main_activity_it.putExtra(CONTACT_ID,main_activity_it.getIntExtra(MainActivity.DIALER_ID,0));
        setResult(RESULT_OK,main_activity_it);
        finish();
    }
}
