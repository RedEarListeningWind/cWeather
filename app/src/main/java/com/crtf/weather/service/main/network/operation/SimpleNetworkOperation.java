package com.crtf.weather.service.main.network.operation;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.request.CoordinateType;
import com.crtf.weather.data.pojo.baidu.general.request.Output;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.geocoding.request.RequestGeocoding;
import com.crtf.weather.data.pojo.baidu.geocoding.response.GeocodingResult;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.request.RequestReverseGeocoding;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.request.Lang;
import com.crtf.weather.data.pojo.colorfulclouds.request.RequestColorfulClouds;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.data.pojo.ui.adapter.ReverseGeocodingAndColorfulClouds;
import com.crtf.weather.util.executors.ThreadPoolExecutorUtil;
import com.crtf.weather.util.jackson.JacksonUtil;
import com.crtf.weather.util.okhttp.OkHttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import okhttp3.FormBody;

/**
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 0:41
 */
public class SimpleNetworkOperation implements NetworkOperation {

    private static final String TAG = "com.crtf.weather.service.main.network.operation.SimpleNetworkOperation";

    private final NetworkObservable networkObservable;

    private final String token = "xxxxxxxxxxxxxx";
    private final String[] colorfulCloudsUrl = {
            "https://api.caiyunapp.com/v2.5/",  // 0 网址
            token,    // 1 token
            "/",
            "0",   // 3 经度
            ",",
            "0",    // 5 纬度
            "/weather.json" // 6
    };
    private final String colorfulCloudsUrlSuffix = createUrlSuffix();

    private final String geocodingUrl = "https://api.map.baidu.com/geocoding/v3/";
    private final String reverseGeocodingUrl = "https://api.map.baidu.com/reverse_geocoding/v3/";

    public SimpleNetworkOperation() {
        networkObservable = new NetworkObservable();
    }

    @Override
    public void addObserver(Observer observer) {
        networkObservable.addObserver(observer);
    }

    @Override
    public void acquisitionWeatherData(Location location, int position) {
        ThreadPoolExecutorUtil.submit(() -> {
            final String urlString = createUrl(location);

            ResponseColorfulClouds colorfulClouds = null;
            try {
                final URL url = new URL(urlString);

                final String json = OkHttpUtil.get(url);
                if (json != null) {
                    colorfulClouds = JacksonUtil.deserializerFromString(new TypeReference<ResponseColorfulClouds>() {
                    }, json);
                } else {
//                Log.e(TAG, "AcquisitionWeatherData: json 为空");
                    System.out.println("AcquisitionWeatherData: json 为空");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return colorfulClouds;
        }, consumerFuture -> {
            final ResponseColorfulClouds colorfulClouds;
            try {
                colorfulClouds = consumerFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return;
            }
            notifyActivity(position, reverseGeocodingAndColorfulClouds -> {
                reverseGeocodingAndColorfulClouds.setResponseColorfulClouds(colorfulClouds);
            });
        });
    }

    /**
     * 通知 Activity( {@link com.crtf.weather.MainActivity.MainServiceConnection.NetworkObserver#update(java.util.Observable, java.lang.Object)} )
     *
     * @param position
     * @param consumer
     */
    private void notifyActivity(int position, Consumer<ReverseGeocodingAndColorfulClouds> consumer) {
        ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds = networkObservable.get(position);
        if (reverseGeocodingAndColorfulClouds == null) {
            reverseGeocodingAndColorfulClouds = new ReverseGeocodingAndColorfulClouds();
            networkObservable.put(position, reverseGeocodingAndColorfulClouds);
        }
        consumer.accept(reverseGeocodingAndColorfulClouds);
        networkObservable.notifyObservers(position);
    }


    private String createUrl(Location location) {
        colorfulCloudsUrl[3] = String.valueOf(location.getLng());
        colorfulCloudsUrl[5] = String.valueOf(location.getLat());

        return String.join("", colorfulCloudsUrl) + colorfulCloudsUrlSuffix;
    }

    private String createUrlSuffix() {
        final RequestColorfulClouds requestColorfulClouds = new RequestColorfulClouds();
        requestColorfulClouds.setLang(Lang.ZH_CN);
        requestColorfulClouds.setAlert(true);
        requestColorfulClouds.setDailySteps(15);
        requestColorfulClouds.setHourlySteps(360);

        StringBuilder urlSuffix = new StringBuilder();
        final boolean[] token = {true};
        OkHttpUtil.extractAllValuesAssignToForm(requestColorfulClouds, (s, o) -> {
            if (token[0]) {
                urlSuffix.append("?");
                token[0] = false;
            } else {
                urlSuffix.append("&");
            }
            urlSuffix.append(s)
                    .append("=")
                    .append(o.toString());

        });
        return urlSuffix.toString();
    }

    @Override
    public BdResponse<GeocodingResult> geocoding(String addr) {
        if (addr != null && !"".equals(addr.trim())) {
            RequestGeocoding requestGeocoding = createRequestGeocoding(addr);

            final FormBody.Builder formBody = new FormBody.Builder();

            OkHttpUtil.extractAllValuesAssignToForm(requestGeocoding, ((s, o) -> formBody.add(s, o.toString())));
            try {
                final String json = OkHttpUtil.post(new URL(geocodingUrl), formBody.build());

                if (json != null && !"".equals(json.trim())) {
                    return JacksonUtil.deserializerFromString(new TypeReference<BdResponse<GeocodingResult>>() {
                    }, json);
                } else return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private RequestGeocoding createRequestGeocoding(String addr) {
        RequestGeocoding requestGeocoding = new RequestGeocoding();

        requestGeocoding.setAddress(addr);
        requestGeocoding.setAk("sxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxo");
        requestGeocoding.setOutput(Output.JSON);
        requestGeocoding.setMcode("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxE:5E;com.crtf.weather");
        return requestGeocoding;
    }

    @Override
    public void reverseGeocoding(Location location, int position) {
        ThreadPoolExecutorUtil.submit(() -> {
            if (location != null) {
                final RequestReverseGeocoding reverseGeocoding = createRequestReverseGeocoding(location);

                final FormBody.Builder fromBody = new FormBody.Builder();

                OkHttpUtil.extractAllValuesAssignToForm(reverseGeocoding, (s, o) -> fromBody.add(s, o.toString()));

                try {
                    final String json = OkHttpUtil.post(new URL(reverseGeocodingUrl), fromBody.build());

                    if (json != null && !"".equals(json.trim())) {
                        return JacksonUtil.deserializerFromString(new TypeReference<BdResponse<ReverseGeocodingResult>>() {
                        }, json);
                    } else return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }, consumerFuture -> {
            final BdResponse<ReverseGeocodingResult> reverseGeocodingResultBdResponse;
            try {
                reverseGeocodingResultBdResponse = consumerFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return;
            }

            notifyActivity(position, reverseGeocodingAndColorfulClouds -> {
                reverseGeocodingAndColorfulClouds.setReverseGeocodingResultBdResponse(reverseGeocodingResultBdResponse);
            });
        });
    }


    private RequestReverseGeocoding createRequestReverseGeocoding(Location location) {
        final RequestReverseGeocoding reverseGeocoding = new RequestReverseGeocoding();
        reverseGeocoding.setLocation(location);
        reverseGeocoding.setCoordinateType(CoordinateType.WGS84LL);
        reverseGeocoding.setRetCoordinateType(CoordinateType.GCJ02LL);
        reverseGeocoding.setRadius(500);
        reverseGeocoding.setAk("Dxxxxxxxxxxxxxxxxxkqo");
        reverseGeocoding.setOutput(Output.JSON);
        reverseGeocoding.setExtensionsPoi(1);
        reverseGeocoding.setExtensionsRoad(true);
        reverseGeocoding.setExtensionsTown(true);
        reverseGeocoding.setLanguage("local");
        reverseGeocoding.setLanguageAuto(1);
        reverseGeocoding.setMcode("0xxxxxxxxxxxxxxxxxxxxxxxxxxxx3E:5E;com.crtf.weather");
        return reverseGeocoding;
    }

    @Override
    public void free() {
        this.networkObservable.deleteObservers();
        this.networkObservable.clear();
        ThreadPoolExecutorUtil.shutdowNow();
    }

    public static final class NetworkObservable extends Observable {

        private final Map<Integer, ReverseGeocodingAndColorfulClouds> reverseGeocodingAndColorfulCloudsMap = new HashMap<>();

        public int size() {
            return this.reverseGeocodingAndColorfulCloudsMap.size();
        }

        public boolean isEmpty() {
            return false;
        }

        @Nullable
        public ReverseGeocodingAndColorfulClouds get(@Nullable Integer key) {
            return this.reverseGeocodingAndColorfulCloudsMap.get(key);
        }

        @Nullable
        public ReverseGeocodingAndColorfulClouds put(Integer key, ReverseGeocodingAndColorfulClouds value) {
            return this.reverseGeocodingAndColorfulCloudsMap.put(key, value);
        }

        @Nullable
        public ReverseGeocodingAndColorfulClouds remove(@Nullable Integer key) {
            return this.reverseGeocodingAndColorfulCloudsMap.remove(key);
        }

        public void clear() {
            this.reverseGeocodingAndColorfulCloudsMap.clear();
        }

        /**
         * @param arg
         */
        @Override
        public void notifyObservers(Object arg) {
            synchronized (this) {
                final ReverseGeocodingAndColorfulClouds reverseGeocodingAndColorfulClouds = get((Integer) arg);
                if (reverseGeocodingAndColorfulClouds.getResponseColorfulClouds() != null &&
                        reverseGeocodingAndColorfulClouds.getReverseGeocodingResultBdResponse() != null) {
                    setChanged();
                }
                super.notifyObservers(arg);
            }
        }
    }

}
