package com.sampleapp.base;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.sampleapp.R;
import com.sampleapp.api.NetModule;
import com.sampleapp.utils.UtilsModule;
import com.sampleapp.BuildConfig;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Initialization of required libraries
 */
public class SampleApplication extends MultiDexApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //create component
        mAppComponent = DaggerAppComponent.builder()
                .utilsModule(new UtilsModule(this)).
                        netModule(new NetModule(this)).build();

        //configure timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
