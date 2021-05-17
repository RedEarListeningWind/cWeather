package com.crtf.weather.ui.main.after;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crtf.weather.R;
import com.crtf.weather.data.pojo.colorfulclouds.response.Daily;
import com.crtf.weather.data.pojo.colorfulclouds.response.Local;
import com.crtf.weather.data.pojo.colorfulclouds.response.WeatherPhenomenon;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


public class AfterWeatherRecyclerAdapter extends RecyclerView.Adapter<AfterWeatherViewHolder> {
public static final String TAG = "com.crtf.weather.ui.main.after.AfterWeatherRecyclerAdapter";
    private  Daily daily = null;

    @NonNull
    @Override
    public AfterWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_card, parent, false);
        return new AfterWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AfterWeatherViewHolder holder, int position) {
        final WeatherPhenomenon weatherPhenomenon = daily.getSkycon().get(position).getValue();
        holder.getCardRoot().setCardBackgroundColor(holder.itemView.getResources().getColor(weatherPhenomenon.getBackgroundColor(null)));
        holder.getWeatherIcon().setImageResource(weatherPhenomenon.getIcon(null));
        holder.getMinimumTemperature().setText(Math.round(daily.getTemperature().get(position).getMin()) + "º");
        holder.getMaximumTemperature().setText(Math.round(daily.getTemperature().get(position).getMax()) + "º");
        holder.getDataWeek().setText("周" + LocalDate.parse(daily.getSkycon().get(position).getDatetime().getDate().getDate()).getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.SIMPLIFIED_CHINESE));
        holder.getDate().setText(daily.getSkycon().get(position).getDatetime().getDate().getDate());
    }

    @Override
    public int getItemCount() {
        return daily == null ? 0 : daily.getSkycon().size();
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}
