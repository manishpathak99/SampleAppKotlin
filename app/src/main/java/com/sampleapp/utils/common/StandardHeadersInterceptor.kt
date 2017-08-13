package com.sampleapp.utils.common

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings

import com.sampleapp.utils.Utility

import java.io.IOException
import java.util.TimeZone

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class StandardHeadersInterceptor(internal var context: Context)//THis is little bad need to do other way !
    : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val version = if (pInfo == null)
            "Could not fetch version."
        else
            pInfo.versionName

        val builder = original.newBuilder()
                .header("Device-Type", "2")
                .header("App-Version", version)
                .header("Os-Version", Utility.osVersion)
                .header("Device-Name", Build.MODEL) //2 denotes android
                .header("TimeZone", TimeZone.getDefault().id)
                .method(original.method(), original.body())

        val uniqueID = Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)
        if (uniqueID != null && !uniqueID.isEmpty()) {
            builder.header("Android-Id", uniqueID)
        }

        return chain.proceed(builder.build())
    }
}