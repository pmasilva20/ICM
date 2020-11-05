package com.example.hw2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDayListAdapter extends RecyclerView.Adapter<RecyclerViewDayListAdapter.ViewHolder> {

    private String[] mDataset;

    private String cityChoosen;
    private Fragment container;


    public RecyclerViewDayListAdapter(String[] dataset, Fragment ipmaDetails) {
        mDataset = dataset;
        container = ipmaDetails;
    }


    @NonNull
    @Override
    public RecyclerViewDayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_date, parent, false);
        RecyclerViewDayListAdapter.ViewHolder vh = new RecyclerViewDayListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDayListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
            v.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            ((IpmaDetails)container).clickedDate(((TextView)view).getText().toString());
        }
    }
}
