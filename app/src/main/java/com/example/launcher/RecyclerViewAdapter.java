package com.example.launcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<RecyclerViewData> data = new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<RecyclerViewData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_city.setText(data.get(position).city_name);
        holder.tv_country.setText(data.get(position).country_name);
        holder.tv_temperature.setText(data.get(position).temperature_celsius);
        holder.tv_description.setText(data.get(position).description_detail);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_city, tv_country, tv_temperature, tv_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_city = itemView.findViewById(R.id.city);
            tv_country = itemView.findViewById(R.id.country);
            tv_temperature = itemView.findViewById(R.id.temperature);
            tv_description = itemView.findViewById(R.id.description);
        }
    }

}
