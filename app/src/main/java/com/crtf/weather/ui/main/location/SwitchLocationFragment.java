package com.crtf.weather.ui.main.location;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crtf.weather.MainActivity;
import com.crtf.weather.R;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.AddressComponent;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.PoiRegions;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.Pois;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.response.Daily;
import com.crtf.weather.data.pojo.colorfulclouds.response.Local;
import com.crtf.weather.data.pojo.colorfulclouds.response.Realtime;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.data.pojo.ui.adapter.ReverseGeocodingAndColorfulClouds;
import com.crtf.weather.ui.main.after.AfterWeatherRecyclerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 20:18
 */
public class SwitchLocationFragment extends Fragment {
    private ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds;

    // region 拿到 View
    // 第一个卡片
    private CardView todayCardView = null;
    // 今天温度
    private TextView todayTemperatureText = null;
    // 今天天气
    private TextView todayWeatherText = null;
    // 今天天气图标
    private ImageView todayWeatherIcon = null;
    // 今天日期
    private TextView todayDateText = null;
    // 空气质量
    private ImageView todayAirQualityicon = null;
    // 空气质量 描述
    private TextView todayAirQualityText = null;
    // 地理位置
    private TextView geographicalLocationText = null;
    // GPS图标
    private ImageView GPSIcon = null;
    // 地理位置集合
    private RelativeLayout geographicalLocationCollection = null;
    // 位置管理
    private ImageView locationManagementIcon = null;
    // 更多
    private ImageView moreIcon = null;
    // 后面的天气
    private RecyclerView afterWeatherRecyclerView = null;
    // endregion

    public SwitchLocationFragment(ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds) {
        this.reverseGeocodingAndColorfulClouds = reverseGeocodingAndColorfulClouds;
    }

    public static SwitchLocationFragment newInstance(ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds) {
        SwitchLocationFragment fragment = new SwitchLocationFragment(reverseGeocodingAndColorfulClouds);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_switch_location, container, false);

        // region 拿到 View
        // 第一个卡片
        this.todayCardView = root.findViewById(R.id.card_view_ui_main_location_switch_location_today);
        // 今天温度
        this.todayTemperatureText = root.findViewById(R.id.text_view_ui_main_location_switch_location_today_temperature);
        // 今天天气
        this.todayWeatherText = root.findViewById(R.id.text_view_ui_main_location_switch_location_today_weather);
        // 今天天气图标
        this.todayWeatherIcon = root.findViewById(R.id.image_view_ui_main_location_switch_location_today_weather);
        // 今天日期
        this.todayDateText = root.findViewById(R.id.text_view_ui_main_location_switch_location_today_date);
        // 空气质量
        this.todayAirQualityicon = root.findViewById(R.id.image_view_ui_main_location_switch_location_air_quality);
        // 空气质量 描述
        this.todayAirQualityText = root.findViewById(R.id.text_view_ui_main_location_switch_location_air_quality);
        // 地理位置
        this.geographicalLocationText = root.findViewById(R.id.text_view_ui_main_location_switch_location_geographical_location);
        // GPS图标
        this.GPSIcon = root.findViewById(R.id.image_view_ui_main_location_switch_location_gps_icon);
        // 地理位置集合
        this.geographicalLocationCollection = root.findViewById(R.id.relative_layout_ui_main_location_switch_location_relative);
        // 位置管理
        this.locationManagementIcon = root.findViewById(R.id.image_view_ui_main_location_switch_location_location_management);
        // 更多
        this.moreIcon = root.findViewById(R.id.image_view_ui_main_location_switch_location_more);
        // 后面的天气
        this.afterWeatherRecyclerView = root.findViewById(R.id.recycler_view_ui_main_location_switch_location_after_weather);
        // endregion

        // region 后面的天气 卡片 绑定
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        afterWeatherRecyclerView.setLayoutManager(linearLayoutManager);
        AfterWeatherRecyclerAdapter afterWeatherRecyclerAdapter = new AfterWeatherRecyclerAdapter();
        afterWeatherRecyclerView.setAdapter(afterWeatherRecyclerAdapter);
        // endregion

        updateUiData();
        return root;
    }


    public void updateUiData() {
        if (this.todayDateText != null) {
            this.todayDateText.setText("周" + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.SIMPLIFIED_CHINESE));
            if (this.reverseGeocodingAndColorfulClouds != null) {
                final ResponseColorfulClouds responseColorfulClouds = this.reverseGeocodingAndColorfulClouds.getResponseColorfulClouds();
                final BdResponse<ReverseGeocodingResult> reverseGeocodingResultBdResponse = this.reverseGeocodingAndColorfulClouds.getReverseGeocodingResultBdResponse();

                this.todayTemperatureText.setText(String.valueOf(Math.round(responseColorfulClouds.getResult().getRealtime().getTemperature())));
                final Realtime realtime = responseColorfulClouds.getResult().getRealtime();
                if (realtime != null) {
                    this.todayAirQualityText.setText(getResources().getText(R.string.today_air_quality_desc) +
                            realtime.getAirQuality().getDescription().getChn() +
                            " " + realtime.getAirQuality().getAqi().getChn());
                    this.todayWeatherText.setText(realtime.getSkycon().getPhenomenon());
                    this.todayCardView.setCardBackgroundColor(getResources().getColor(realtime.getSkycon().getBackgroundColor(null)));

                    Integer icon = null;
                    if (realtime.getSkycon().isSunnyDay()) {
                        final LocalDateTime dateTime = LocalDateTime.now();
                        if (dateTime.getHour() > 5 && dateTime.getHour() < 9) {
                            icon = realtime.getSkycon().getIconSunrise(null);
                        } else if (dateTime.getHour() > 16 && dateTime.getHour() < 20) {
                            icon = realtime.getSkycon().getIconSunset(null);
                        }
                    }
                    if (icon == null) {
                        icon = realtime.getSkycon().getIcon(null);
                    }
                    this.todayWeatherIcon.setImageResource(icon);
                }
                AfterWeatherRecyclerAdapter adapter = (AfterWeatherRecyclerAdapter) this.afterWeatherRecyclerView.getAdapter();
                if (adapter != null) {
                    adapter.setDaily(responseColorfulClouds.getResult().getDaily());
                    adapter.notifyItemRangeInserted(0, adapter.getItemCount());
                }


                List<String> stringList = new ArrayList<>();
                /*
                if (reverseGeocodingResultBdResponse.getResult().getPoiRegions() != null) {
                    final List<PoiRegions> poiRegionsList = reverseGeocodingResultBdResponse.getResult().getPoiRegions();
                    if (poiRegionsList.size() > 0) {
                        final PoiRegions poiRegions = poiRegionsList.get(0);
                        if (poiRegions != null) {
                            stringList.add(poiRegions.getName());
                        }
                    }
                }
                final List<Pois> pois = reverseGeocodingResultBdResponse.getResult().getPois();
                if (pois != null && pois.size() > 0) {
                    final Pois pois0 = pois.get(0);
                    if (pois0 != null) {
                        stringList.add(pois0.getName());
                    }
                }

                 */
                final AddressComponent addressComponent = reverseGeocodingResultBdResponse.getResult().getAddressComponent();
//                stringList.add(addressComponent.getStreet());
//                stringList.add(addressComponent.getTown());
                stringList.add(addressComponent.getDistrict());
                stringList.add(addressComponent.getCity());
                stringList.add(addressComponent.getProvince());
                stringList.add(addressComponent.getCountry());

                String address = null;
                for (int i = 0; i < stringList.size(); i++) {
                    address = stringList.get(i);
                    if (!isAddressNameEmpty(address)) {
                        break;
                    }
                }
                if (isAddressNameEmpty(address)) {
                    address = "未知地区";
                }
                this.geographicalLocationText.setText(address);
            }
        }
    }

    /**
     * @param addressName 地址名
     * @return 是否为空
     */
    private boolean isAddressNameEmpty(String addressName) {
        return addressName == null || "".equals(addressName);
    }

    public ReverseGeocodingAndColorfulClouds getReverseGeocodingAndColorfulClouds() {
        return reverseGeocodingAndColorfulClouds;
    }

    public void setReverseGeocodingAndColorfulClouds(ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds) {
        this.reverseGeocodingAndColorfulClouds = reverseGeocodingAndColorfulClouds;
    }
}