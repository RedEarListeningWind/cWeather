package com.crtf.weather.service.main.local.file;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.crtf.weather.service.main.local.file.operation.FileOperation;
import com.crtf.weather.service.main.local.file.operation.SimpleFileOperation;

public class FileService extends Service {

    // region 文件服务
    private AllOperation allOperation;

    public FileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return allOperation;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    // endregion

    static class AllOperation extends Binder implements FileOperation{
        private final FileOperation fileOperation;

        public AllOperation() {
            this.fileOperation = new SimpleFileOperation();
        }
    }
}