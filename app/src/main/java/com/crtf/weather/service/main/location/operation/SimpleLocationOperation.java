package com.crtf.weather.service.main.location.operation;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.crtf.weather.data.pojo.baidu.general.Location;

import java.util.Observable;
import java.util.Observer;

/**
 * 此类表示模型视图范例中的可观察对象或“数据”。 可以将其子类化以表示应用程序想要观察的对象。
 * 一个可观察对象可以具有一个或多个观察者。 观察者可以是实现接口Observer的任何对象。
 * 可观察实例发生更改后，调用Observable的notifyObservers方法的应用程序将通过调用其update方法来通知其所有观察者更改。
 * 未指定发送通知的顺序。 Observable类中提供的默认实现将以观察者注册兴趣的顺序通知观察者，
 * 但是子类可以更改此顺序，不使用保证的顺序，在单独的线程上传递通知或可以保证其子类遵循此顺序，因为它们选择。
 * 请注意，此通知机制与线程无关，并且与Object类的wait和notify机制完全分开。
 * 当新创建一个可观察对象时，其观察者集合为空。 当且仅当equals方法为其返回true时，两个观察者才被视为相同
 *
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 0:41
 */
public class SimpleLocationOperation implements LocationOperation {
    public static final String TAG = "com.crtf.weather.service.main.location.operation.SimpleLocationOperation";
    /**
     * 可观察的
     */
    private final LocationObservable locationObservable;

    private final Service service;
    private final LocationManager locationManager;

    private final LocationListenerController locationListenerController;

    public SimpleLocationOperation(Service service) {
        this.service = service;
        LocationManager locationManager = (LocationManager) this.service.getSystemService(Context.LOCATION_SERVICE);
        this.locationManager = locationManager;
        this.locationObservable = new LocationObservable();
        locationListenerController = new LocationListenerController();
    }

    @Override
    public void addObserver(Observer observer) {
        locationObservable.addObserver(observer);
    }



    @Override
    public void positioning() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
        criteria.setAltitudeRequired(false);//无海拔要求
        criteria.setBearingRequired(false);//无方位要求
        criteria.setCostAllowed(true);//允许产生资费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        // 获取最佳服务对象
        String provider = locationManager.getBestProvider(criteria, true);
        // 最后的已知位置
        if (ActivityCompat.checkSelfPermission(this.service.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.service.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: 考虑在此处调用ActivityCompatrequestPermissions以请求缺少的权限，
            //  然后重写
            //  public void onRequestPermissionsResult（int requestCode，String []权限，int [] grantResults）
            //  来处理用户授予权限的情况。有关更多详细信息，请参见ActivityCompatrequestPermissions文档。
            Toast.makeText(this.service.getApplicationContext(), "未授于定位权限!", Toast.LENGTH_LONG).show();
            return;
        }
        if (provider != null) {
            Log.i(TAG, "positioning: provider: " + provider);
            android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation != null) {
                locationListenerController.reviseLocation(lastKnownLocation);
            }
        }

        if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListenerController.networkListener);
        }
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListenerController.gpsLocationListener);
        }
    }

    private class LocationListenerController {
        private static final int TWO_MINUTES = 1000 * 60 * 2;

        private android.location.Location location;
        /**
         * 网络定位
         */
        private final LocationListener networkListener = new LocationListener() {
            // region
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            // endregion
            @Override
            public void onLocationChanged(@NonNull android.location.Location location) {
                reviseLocation(location);
                Log.i(TAG, "onLocationChanged: 网络定位: (" + location.getLongitude() + "," + location.getLatitude() + ")");
                locationManager.removeUpdates(this);
            }

        };

        /**
         * GPS 定位
         */
        private final LocationListener gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull android.location.Location location) {
                reviseLocation(location);
                Log.i(TAG, "onLocationChanged: GPS 定位: (" + location.getLongitude() + "," + location.getLatitude() + ")");
                locationManager.removeUpdates(this);
            }

            // region
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }
            // endregion
        };

        /**
         * 修正位置
         */
        private void reviseLocation(android.location.Location location) {
            if (location != null && isBetterLocation(location, this.location)) {
                locationObservable.setLocation(location.getLatitude(), location.getLongitude());
                locationObservable.notifyObservers();
            }
        }

        /**
         * 确定一个位置读数是否比当前位置修正更好
         *
         * @param location            您要评估的新位置
         * @param currentBestLocation 您要与新位置信息进行比较的当前位置信息修复程序
         */
        protected boolean isBetterLocation(android.location.Location location,
                                           android.location.Location currentBestLocation) {
            if (currentBestLocation == null) {
                // 新位置总比没有位置好
                return true;
            }

            // 检查新的位置修复是新的还是旧的
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
            boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
            boolean isNewer = timeDelta > 0;

            // 如果距当前位置已超过两分钟，请使用
            // 新地点
            // 因为用户可能已经移动
            if (isSignificantlyNewer) {
                return true;
                // 如果新位置的时间早于两分钟，则必须是
                // 更坏
            } else if (isSignificantlyOlder) {
                return false;
            }

            // 检查新的位置修正或多或少准确
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
                    .getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // 检查旧位置和新位置是否来自同一提供商
            boolean isFromSameProvider = isSameProvider(location.getProvider(),
                    currentBestLocation.getProvider());

            // 结合时效性和
            // 准确性
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate
                    && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * 检查两个提供者是否相同
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }
    }

    @Override
    public void free() {
        this.locationObservable.deleteObservers();
        this.locationListenerController.location = null;
    }

    public static final class LocationObservable extends Observable {

        private final Location location = new Location();

        public Location getLocation() {
            Location clone = new Location();
            clone.setLat(location.getLat());
            clone.setLng(location.getLng());
            return clone;
        }

        public void setLocation(Location location) {
            setLocation(location.getLat(), location.getLng());
        }

        /**
         * @param lat 纬度
         * @param lng 经度
         */
        public void setLocation(double lat, double lng) {
            this.location.setLat(lat);
            this.location.setLng(lng);
            this.setChanged();
        }

        @Override
        public String toString() {
            return "(" + location.getLng() + "," + location.getLat() + ")";
        }
    }
}
