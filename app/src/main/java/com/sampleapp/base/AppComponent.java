package com.sampleapp.base;

import com.sampleapp.api.NetModule;
import com.sampleapp.utils.common.TokenAuthenticator;
import com.sampleapp.module.SplashActivity;
import com.sampleapp.notification.fcm.TokenService;
import com.sampleapp.notification.fcm.UpdateTokenJob;
import com.sampleapp.module.sampleactivity.view.SampleActivity;
import com.sampleapp.module.samplefragment.view.SampleFragment;
import com.sampleapp.utils.LocationTracker;
import com.sampleapp.utils.UtilsModule;
import org.jetbrains.annotations.NotNull;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Injections for Application class
 */

@Singleton
@Component(modules = {UtilsModule.class, NetModule.class})
public interface AppComponent {

    void inject(@NotNull SplashActivity splashActivity);

    void inject(@NotNull LocationTracker locationTracker);

    void inject(@NotNull TokenAuthenticator tokenAuthenticator);

    void inject(@NotNull TokenService tokenService);

    void inject(@NotNull UpdateTokenJob updateTokenJob);

    void inject(@NotNull SampleFragment sampleFragment);

    void inject(@NotNull SampleActivity sampleActivity);

}
