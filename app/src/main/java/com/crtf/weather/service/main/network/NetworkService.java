package com.crtf.weather.service.main.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.geocoding.response.GeocodingResult;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.service.main.network.operation.NetworkOperation;
import com.crtf.weather.service.main.location.operation.LocationOperation;
import com.crtf.weather.service.main.network.operation.SimpleNetworkOperation;
import com.crtf.weather.service.main.location.operation.SimpleLocationOperation;

import java.util.Observer;

/**
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 0:41
 */
public class NetworkService extends Service {

    // region 网络服务
    private InternalNetworkOperation internalNetworkOperation;

    public NetworkService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return internalNetworkOperation;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        internalNetworkOperation = new InternalNetworkOperation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.internalNetworkOperation.free();
    }
    // endregion

    // region 内部网络操作
    public static class InternalNetworkOperation extends Binder implements NetworkOperation {
        private final NetworkOperation simpleNetworkOperation;

        InternalNetworkOperation() {
            simpleNetworkOperation = new SimpleNetworkOperation();
        }


        @Override
        public void addObserver(Observer observer) {
            simpleNetworkOperation.addObserver(observer);
        }


        @Override
        public void acquisitionWeatherData(Location location, int position) {
            simpleNetworkOperation.acquisitionWeatherData(location, position);
        }

        @Override
        public BdResponse<GeocodingResult> geocoding(String addr) {
            return simpleNetworkOperation.geocoding(addr);
        }

        @Override
        public void reverseGeocoding(Location location, int position) {
            simpleNetworkOperation.reverseGeocoding(location, position);
        }

        @Override
        public void free() {
            simpleNetworkOperation.free();
        }
    }
    // endregion

}
