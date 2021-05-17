package com.crtf.weather.ui.main.after;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crtf.weather.R;

public class AfterWeatherViewHolder extends RecyclerView.ViewHolder {

    // item 单个 卡片根视图
    private CardView cardRoot;
    // 天气图标
    private ImageView weatherIcon;
    // 最低温度
    private TextView minimumTemperature;
    // 最高温度
    private TextView maximumTemperature;
    // 星期
    private TextView dataWeek;
    // 日期
    private TextView date;

    public AfterWeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        this.cardRoot = itemView.findViewById(R.id.card_view_view_item_weather_card_root);
        this.weatherIcon = itemView.findViewById(R.id.image_view_item_weather_card_weather_icon);
        this.minimumTemperature = itemView.findViewById(R.id.text_view_item_weather_card_minimum_temperature);
        this.maximumTemperature = itemView.findViewById(R.id.text_view_item_weather_card_maximum_temperature);
        this.dataWeek = itemView.findViewById(R.id.text_view_item_weather_card_date_week);
        this.date = itemView.findViewById(R.id.text_view_item_weather_card_date);
    }

    public CardView getCardRoot() {
        return cardRoot;
    }

    public void setCardRoot(CardView cardRoot) {
        this.cardRoot = cardRoot;
    }

    public ImageView getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(ImageView weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public TextView getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(TextView minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public TextView getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(TextView maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public TextView getDataWeek() {
        return dataWeek;
    }

    public void setDataWeek(TextView dataWeek) {
        this.dataWeek = dataWeek;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AfterWeatherViewHolder{" +
                "cardRoot=" + cardRoot +
                ", weatherIcon=" + weatherIcon +
                ", minimumTemperature=" + minimumTemperature +
                ", maximumTemperature=" + maximumTemperature +
                ", dataWeek=" + dataWeek +
                ", date=" + date +
                '}';
    }
}
