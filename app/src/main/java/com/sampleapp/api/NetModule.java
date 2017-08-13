package com.sampleapp.api;

import android.content.Context;

import com.sampleapp.utils.common.TokenAuthenticator;
import com.sampleapp.utils.common.TokenService;
import com.sampleapp.constants.ApiConstants;
import com.sampleapp.utils.PreferenceManager;
import com.sampleapp.utils.common.StandardHeadersInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * contains object related to network communication and API consumption
 */
@Module
public class NetModule {

    Context context; // bad way!
    PreferenceManager mPrefs;

    public NetModule(Context context) {
        this.context = context;
        mPrefs = new PreferenceManager(context);

    }


    @Singleton
    @Provides
    RestService getRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }


    @Singleton
    @Provides
    TokenService providesTokenService(Retrofit retrofit) {
        return retrofit.create(TokenService.class);
    }

    @Singleton
    @Provides
    public Retrofit providersRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    //get OkHttp instance
    @Singleton
    @Provides
    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new IDHeadersInterceptor());
        builder.addInterceptor(new StandardHeadersInterceptor(context));
        builder.interceptors().add(httpLoggingInterceptor);
        builder.authenticator(new TokenAuthenticator(mPrefs));

        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }

    public class IDHeadersInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            if (!mPrefs.getSessionToken().isEmpty())
                builder.header("Authorization", "Bearer " + mPrefs.getSessionToken());
            return chain.proceed(builder.build());
        }
    }

}