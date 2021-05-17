package com.crtf.weather.service.main.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.service.main.location.operation.LocationOperation;
import com.crtf.weather.service.main.location.operation.SimpleLocationOperation;

import java.util.Observable;
import java.util.Observer;
/**
 * @author crtf
 * @date 2021年5月15日 星期六 0:41
 * @version 1.0
 */
public class LocationService extends Service {

    // region 定位服务
    private InternalLocationOperation internalLocationOperation;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        internalLocationOperation = new InternalLocationOperation(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return internalLocationOperation;
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
        this.internalLocationOperation.free();
    }
    // endregion 

    // region 内部定位操作
    public static final class InternalLocationOperation extends Binder implements LocationOperation  {

        private final SimpleLocationOperation locationOperation;
        private final Service service;

        private InternalLocationOperation(Service service) {
            this.service = service;
            this.locationOperation = new SimpleLocationOperation(service);
        }

        @Override
        public void addObserver(Observer observer) {
            locationOperation.addObserver(observer);
        }



        @Override
        public void positioning() {
            this.locationOperation.positioning();
        }

        @Override
        public void free() {
locationOperation.free();
        }
    }
    // endregion 
}