package com.example.ca1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CONTACT_SAVED = 1;
    public static final String NUMBER_DIALED =
            "icm.CA1.extra.NUMBER_DIALED";
    public static final String DIALER_ID =
            "icm.CA1.extra.DIALER_ID";
    public static final String LOG_TAG = "icm.CA1.mainActivity";

    private HashMap<Integer, List<String>> speedDialContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedDialContacts = new HashMap<>();
        //Setup long click listeners
        View v1 = findViewById(R.id.dial1);
        View v2 = findViewById(R.id.dial2);
        View v3 = findViewById(R.id.dial3);
        v1.setOnLongClickListener(longClick);
        v2.setOnLongClickListener(longClick);
        v3.setOnLongClickListener(longClick);
    }

    View.OnLongClickListener longClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Log.i(LOG_TAG,"Long click");
            saveDial(v);
            return true;
        }
    };

    public void getDial(View view){
        if(speedDialContacts.containsKey(view.getId())){
            TextView numberTV = (TextView)findViewById(R.id.number_dialed);
            numberTV.setText(speedDialContacts.get(view.getId()).get(1));
        }
    }

    private void setupDialButton(int id){
        Log.i(LOG_TAG,"U used this R.id "+String.valueOf(id));
        if(speedDialContacts.containsKey(id)){
            ((Button)findViewById(id)).setText(speedDialContacts.get(id).get(0));
        }
    }


    public void dialNumber(View view) {
        String button_number = ((Button)view).getText().toString();
        TextView numberTV = (TextView)findViewById(R.id.number_dialed);
        String plc = numberTV.getText().toString() + button_number;
        numberTV.setText(plc);
    }

    public void cleanDial(View view){
        TextView numberTV = (TextView)findViewById(R.id.number_dialed);
        numberTV.setText("");
    }

    public void handleCall(View view){
        String numberDialed = ((TextView)findViewById(R.id.number_dialed)).getText().toString();
        Uri uri = Uri.parse("tel:"+numberDialed);
        Intent it = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(it);
    }

    //TODO:Find a way of sending the button id pressed,we need it as a key for HM for double touch
    public void saveDial(View view){
        Intent it = new Intent(this,speedDial.class);
        Log.i(LOG_TAG, String.valueOf(view.getResources().getResourceEntryName(view.getId())));
        it.putExtra(DIALER_ID,view.getId());
        startActivityForResult(it,CONTACT_SAVED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_SAVED) {
            if (resultCode == RESULT_OK) {
                Log.i(LOG_TAG,"Just returned from contacts");
                String new_contact_name = data.getStringExtra(speedDial.CONTACT_NAME);
                String new_contact_number = data.getStringExtra(speedDial.CONTACT_NUMBER);
                List<String> lst = new ArrayList<>();
                lst.add(new_contact_name);
                lst.add(new_contact_number);
                Integer contact_id = data.getIntExtra(speedDial.CONTACT_ID,0);
                speedDialContacts.put(contact_id,lst);
                //Get for each id,put stuff there
                setupDialButton(R.id.dial1);
                setupDialButton(R.id.dial2);
                setupDialButton(R.id.dial3);
                Log.i(LOG_TAG,speedDialContacts.toString());
                TextView numberTV = (TextView)findViewById(R.id.number_dialed);
                numberTV.setText(new_contact_number);
            }
        }
    }
}
