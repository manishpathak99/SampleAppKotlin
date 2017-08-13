package com.sampleapp.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import com.sampleapp.utils.common.PhotoHelper;
import com.sampleapp.constants.ValueConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Provide injects for utility objects
 */

@Module
public class UtilsModule {

    private Context mContext;

    public UtilsModule(Context context) {
        this.mContext = context;
    }

    // get AppUtils instance
    @Provides
    @Singleton
    public AppUtils getAppUtils() {
        return new AppUtils(mContext);
    }

    // get DateTimeUtils instance
    @Provides
    @Singleton
    public DateTimeUtils getDateTimeUtils() {
        return new DateTimeUtils();
    }

    //get dialog utils
    @Provides
    @Singleton
    public DialogsUtil getDialogUtils() {
        return new DialogsUtil();
    }

    //get new thread
    @Provides
    @Singleton
    @Named(ValueConstants.NEW_THREAD)
    public Scheduler getNewThread() {return Schedulers.io();
    }

    //get main thread
    @Provides
    @Singleton
    @Named(ValueConstants.MAIN_THREAD)
    public Scheduler getMainThread() { return AndroidSchedulers.mainThread();
    }

    //get Preference Manager
    @Provides
    @Singleton
    public PreferenceManager getPreferences() {
        return new PreferenceManager(mContext);
    }

    //get Fragment utils
    @Provides
    @Singleton
    public FragmentUtils getFragUtils() {
        return new FragmentUtils();
    }

    //get location helper methods
    @Provides
    @Singleton
    public LocationHelper getLocationUtils() {
        return new LocationHelper(mContext);
    }

    //get location tracker
    @Provides
    @Singleton
    public LocationTracker getLocationTrackerOF() {
        return new LocationTracker(mContext);
    }

    //get image utils
    @Provides
    @Singleton
    public ImageUtility getImageUtils() {
        return new ImageUtility(mContext);
    }

    @Provides
    @Singleton
    public PackageManager providesPackageManger() {
        return mContext.getPackageManager();
    }

    @Provides
    @Singleton
    public PhotoHelper providesPhotoHelper(PackageManager manager) {
        return new PhotoHelper(manager, mContext);
    }

    @Provides
    @Singleton
    public Context providesAppContext(PackageManager manager) {
        return mContext;
    }


}
