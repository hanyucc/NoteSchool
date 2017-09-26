package io.github.leniumc.noteschool;

import android.app.Application;

import net.gotev.uploadservice.UploadService;

/**
 * Created by 陈涵宇 on 2017/9/21.
 */

public class Initializer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
    }
}
