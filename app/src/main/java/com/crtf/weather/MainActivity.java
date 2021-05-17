package com.crtf.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.data.pojo.ui.adapter.ReverseGeocodingAndColorfulClouds;
import com.crtf.weather.service.main.location.LocationService;
import com.crtf.weather.service.main.location.operation.SimpleLocationOperation;
import com.crtf.weather.service.main.network.NetworkService;
import com.crtf.weather.service.main.network.operation.SimpleNetworkOperation;
import com.crtf.weather.ui.main.location.SwitchLocationAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 0:41
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "com.crtf.weather.MainActivity";

    private LocationService.InternalLocationOperation locationOperation;
    private NetworkService.InternalNetworkOperation networkOperation;

    Intent intentNetworkService;

    private MainServiceConnection mainServiceConnection;

    private ViewPager switchLocation;
    private SwitchLocationAdapter switchLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));

        // region 拿到 View
        this.switchLocation = findViewById(R.id.view_pager_switch_location_main);
        // endregion

        // region 加载资源
        // endregion

        switchLocationAdapter = new SwitchLocationAdapter(getSupportFragmentManager());
        this.switchLocation.setAdapter(switchLocationAdapter);

        requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, 0x33);
        openGPS();
    }

    // 判断是否开启 GPS ，若未开启，打开 GPS 设置界面
    private void openGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
                || locationManager
                .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
        ) {
            Toast.makeText(this, " 位置源已设置! ", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, " 位置源未设置! ", Toast.LENGTH_LONG).show();

        // 转至 GPS 设置界面
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.startActivityForResult(intent, 36);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 36) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager
                    .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
                    || locationManager
                    .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
            ) {
                Toast.makeText(this, " 位置源已设置! ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, " 位置源设置失败! ", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x33 &&
                grantResults.length == 2
        ) {
            if (mainServiceConnection == null) {
                mainServiceConnection = new MainServiceConnection();
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                final Intent intentNetworkService = new Intent(this, NetworkService.class);
                bindService(intentNetworkService, mainServiceConnection, Service.BIND_AUTO_CREATE);
            }
            if (
                // grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                final Intent intentLocationService = new Intent(this, LocationService.class);
                bindService(intentLocationService, mainServiceConnection, Service.BIND_AUTO_CREATE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this.mainServiceConnection);
        finish();
        ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(getPackageName());
        System.exit(0);
    }

    private final class MainServiceConnection implements ServiceConnection {

        private final LocationObserver locationObserver;
        private final NetworkObserver networkObserver;

        public MainServiceConnection() {
            this.locationObserver = new LocationObserver();
            this.networkObserver = new NetworkObserver();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (name.getClassName().equals(LocationService.class.getName())) {
                MainActivity.this.locationOperation = (LocationService.InternalLocationOperation) service;
                MainActivity.this.locationOperation.addObserver(locationObserver);
            } else if (name.getClassName().equals(NetworkService.class.getName())) {
                MainActivity.this.networkOperation = (NetworkService.InternalNetworkOperation) service;
                MainActivity.this.networkOperation.addObserver(networkObserver);
            }
            if (MainActivity.this.locationOperation != null && MainActivity.this.networkOperation != null) {
                MainActivity.this.locationOperation.positioning();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        private final class LocationObserver implements Observer {

            private Location location = null;

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            @Override
            public void update(Observable o, Object arg) {
                this.location = ((SimpleLocationOperation.LocationObservable) o).getLocation();
                MainActivity.this.networkOperation.reverseGeocoding(this.location, 0);
                MainActivity.this.networkOperation.acquisitionWeatherData(this.location, 0);
                Log.i(TAG, "update: Thread.currentThread().getName(): " + Thread.currentThread().getName() + " this.location: " + this.location);
            }
        }

        private final class NetworkObserver implements Observer {

            @Override
            public void update(Observable o, Object arg) {
                final Integer position = (Integer) arg;
                final SimpleNetworkOperation.NetworkObservable networkObservable = (SimpleNetworkOperation.NetworkObservable) o;
                final ReverseGeocodingAndColorfulClouds networkObservableReverseGeocodingAndColorfulClouds = networkObservable.get(position);
                if (networkObservableReverseGeocodingAndColorfulClouds != null &&
                        networkObservableReverseGeocodingAndColorfulClouds.getResponseColorfulClouds() != null &&
                        networkObservableReverseGeocodingAndColorfulClouds.getReverseGeocodingResultBdResponse() != null) {
                    MainActivity.this.switchLocationAdapter.getReverseGeocodingAndColorfulCloudsMap().put((int) arg, networkObservableReverseGeocodingAndColorfulClouds);
                    // 在UI线程上运行指定的操作。 如果当前线程是UI线程，则立即执行该操作。 如果当前线程不是UI线程，则将操作发布到UI线程的事件队列。
                    runOnUiThread(() -> {
                        MainActivity.this.switchLocationAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "update: Thread.currentThread().getName(): " + Thread.currentThread().getName() + " networkObservableReverseGeocodingAndColorfulClouds: " + networkObservableReverseGeocodingAndColorfulClouds);
                    networkObservable.remove(position);
                }
            }
        }
    }


}